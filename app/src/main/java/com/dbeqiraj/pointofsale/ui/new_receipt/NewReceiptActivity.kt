package com.dbeqiraj.pointofsale.ui.new_receipt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.di.component.DaggerNewReceiptComponent
import com.dbeqiraj.pointofsale.di.module.NewReceiptModule
import com.dbeqiraj.pointofsale.ui.cart.CartActivity
import com.dbeqiraj.pointofsale.ui.new_receipt.extensions.deleteEmptyReceipt
import com.dbeqiraj.pointofsale.ui.new_receipt.extensions.getItems
import com.dbeqiraj.pointofsale.ui.new_receipt.extensions.getReceipt
import com.dbeqiraj.pointofsale.ui.new_receipt.extensions.setupFooter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptPresenter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptRowPresenter
import com.dbeqiraj.pointofsale.vp.view.CurrentSaleView
import kotlinx.android.synthetic.main.content_new_receipt.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class NewReceiptActivity : BaseActivity(), CurrentSaleView {

    var receipt: Receipt? = null
    lateinit var imm: InputMethodManager

    @Inject
    internal lateinit var db: PosDatabase

    @Inject
    internal lateinit var receiptPresenter: ReceiptPresenter

    @Inject
    internal lateinit var receiptRowPresenter: ReceiptRowPresenter

    init {
        menu = R.menu.new_receipt_menu
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setSupportActionBar(toolbar)
        showBackArrow()
        setOnClickListeners()
        getReceipt()
    }

    internal fun updateUIOnReceiptReady() {
        title = String.format(getString(R.string.receipt_nr), receipt!!.id)
        getItems()
        setupFooter()
    }

    fun setOnClickListeners() {
        add_item.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("receipt", receipt)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        deleteEmptyReceipt()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        imm.hideSoftInputFromWindow(add_item.getWindowToken(), 0)
        when (item?.itemId) {
            R.id.action_done -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun resolveDaggerDependency() {
        DaggerNewReceiptComponent.builder()
                .applicationComponent(getApplicationComponent())
                .newReceiptModule(NewReceiptModule(this))
                .build().inject(this)
    }

    override fun getContentView(): Int = R.layout.activity_new_receipt
}
