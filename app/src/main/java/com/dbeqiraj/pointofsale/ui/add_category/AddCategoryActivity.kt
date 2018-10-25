package com.dbeqiraj.pointofsale.ui.add_category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.di.component.DaggerAddCategoryComponent
import com.dbeqiraj.pointofsale.di.module.AddCategoryModule
import com.dbeqiraj.pointofsale.base.extensions.hideDialog
import com.dbeqiraj.pointofsale.base.extensions.showDialog
import com.dbeqiraj.pointofsale.vp.view.AddCategoryView
import kotlinx.android.synthetic.main.content_add_category.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class AddCategoryActivity : BaseActivity(), AddCategoryView {

    private lateinit var imm: InputMethodManager

    @Inject
    protected lateinit var db: PosDatabase

    init {
        menu = R.menu.add_category_menu
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        title = getString(R.string.add_categori_label)
        setSupportActionBar(toolbar)
        showBackArrow()
        focusConfigs()
    }

    private fun focusConfigs() {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        category.setOnFocusChangeListener { view: View, b: Boolean ->
            takeUnless { category.hasFocus() }?.apply {
                imm.hideSoftInputFromWindow(category.getWindowToken(), 0)
            }
        }
        category.requestFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_done -> {
                validateInput()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateInput() {
        if (category.text.toString().trim().isNotEmpty()) {
            insertCategory()
        } else {
            category.error = getString(R.string.required_field)
        }
    }

    private fun insertCategory() {
        onShowDialog()
        doAsync {
            val cat = Category(category.text.toString())
            val id: Long? = try {
                db.categoryDao().insert(cat)
            } catch (e: Exception) {
                null
            }

            uiThread {
                hideDialog()
                id?.let {
                    finish()
                } ?: run {
                    category.error = getString(R.string.category_already_exists)
                }
            }
        }
    }

    override fun onShowDialog() = showDialog(getString(R.string.saving_category))

    override fun onHideDialog() = hideDialog()

    override fun getContentView(): Int = R.layout.activity_add_category

    override fun resolveDaggerDependency() {
        DaggerAddCategoryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addCategoryModule(AddCategoryModule(this))
                .build().inject(this)
    }
}
