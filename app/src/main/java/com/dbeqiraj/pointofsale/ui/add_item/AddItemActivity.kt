package com.dbeqiraj.pointofsale.ui.add_item

import android.arch.lifecycle.LiveData
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.database.entity.Item
import com.dbeqiraj.pointofsale.di.component.DaggerAddItemComponent
import com.dbeqiraj.pointofsale.di.module.AddItemModule
import com.dbeqiraj.pointofsale.ui.add_item.extensions.*
import com.dbeqiraj.pointofsale.base.extensions.hideDialog
import com.dbeqiraj.pointofsale.base.extensions.showDialog
import com.dbeqiraj.pointofsale.vp.presenter.CategoryPresenter
import com.dbeqiraj.pointofsale.vp.view.AddItemView
import kotlinx.android.synthetic.main.content_add_item.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class AddItemActivity : BaseActivity(), AddItemView {

    lateinit var categoriesLiveData: LiveData<MutableList<Category>>
    lateinit var imm: InputMethodManager

    @Inject
    internal lateinit var categoryPresenter: CategoryPresenter

    @Inject
    protected lateinit var db: PosDatabase

    init {
        menu = R.menu.add_item_menu
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        title = getString(R.string.add_item)
        setSupportActionBar(toolbar)
        showBackArrow()
        setupCategorySpinner()
        focusConfigs()
        setupTaxSection()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        imm.hideSoftInputFromWindow(name.getWindowToken(), 0)
        when (item?.itemId) {
            R.id.action_done -> {
                validateInput()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateInput() {
        var allSet = true
        for (view in listOf(name, barcode, price)) {
            when (view.text?.isEmpty()) {
                true -> {
                    view.error = getString(R.string.required_field)
                    allSet = false
                }
                else -> view.error = null
            }
        }
        when (category.selectedItemPosition) {
            0 -> {
                (category.selectedView as TextView).error = getString(R.string.select_category)
                allSet = false
            }
            else -> (category.selectedView as TextView).error = null
        }
        takeIf { allSet }?.apply { addItem() }
    }

    private fun addItem() {
        onShowDialog()
        doAsync {
            val item = prepareItem()
            val id: Long? = try {
                db.itemDao().insert(item)
            } catch (e: Exception) {
                null
            }

            uiThread {
                hideDialog()
                id?.let {
                    finish()
                } ?: run {
                    onShowToast(getString(R.string.cannot_add_item))
                }
            }
        }
    }

    private fun prepareItem(): Item {
        val name = name.text.toString()
        val categoryId = categoriesLiveData.value!!.filter { cat -> cat.name == (category.selectedItem as String) }.single().id
        val vat: Float = if (vat.text.toString().isBlank()) 0F else vat.text.toString().toFloat()
        val barcode = barcode.text.toString()
        val price: Float = if (price.text.toString().isBlank()) 0F else price.text.toString().toFloat()
        val cost: Float = if (cost.text.toString().isBlank()) 0F else cost.text.toString().toFloat()

        return Item(name, price, vat, cost, barcode, categoryId)
    }

    override fun onShowDialog() = showDialog(getString(R.string.saving_item))

    override fun onHideDialog() = hideDialog()

    override fun onShowToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun getContentView(): Int = R.layout.activity_add_item

    override fun resolveDaggerDependency() {
        DaggerAddItemComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addItemModule(AddItemModule(this))
                .build().inject(this)
    }
}
