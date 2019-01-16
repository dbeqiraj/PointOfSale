package com.dbeqiraj.pointofsale.ui.new_receipt

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.di.component.DaggerNewReceiptComponent
import com.dbeqiraj.pointofsale.di.module.NewReceiptModule
import com.dbeqiraj.pointofsale.ui.cart.CartActivity
import com.dbeqiraj.pointofsale.ui.new_receipt.adapter.ItemAdapter
import com.dbeqiraj.pointofsale.ui.new_receipt.extensions.getItems
import com.dbeqiraj.pointofsale.ui.new_receipt.extensions.setupFooter
import com.dbeqiraj.pointofsale.utilities.Constants.RESULT_CODE_FAILED
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptPresenter
import com.dbeqiraj.pointofsale.vp.presenter.ReceiptRowPresenter
import com.dbeqiraj.pointofsale.vp.view.CurrentSaleView
import kotlinx.android.synthetic.main.content_new_receipt.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class NewReceiptActivity : BaseActivity(), CurrentSaleView {

    var receipt: Receipt? = null

    @Inject
    protected lateinit var db: PosDatabase

    @Inject
    internal lateinit var receiptPresenter: ReceiptPresenter

    @Inject
    internal lateinit var receiptRowPresenter: ReceiptRowPresenter

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        setSupportActionBar(toolbar)
        showBackArrow()
        setOnClickListeners()
        getReceipt()
    }

    private fun getReceipt() {
        takeIf { intent.hasExtra("receipt") }?.apply { receipt = intent.getSerializableExtra("receipt") as Receipt }

        if (receipt != null)
            updateUIOnReceiptReady()
        else
            openNewReceipt()
    }

    private fun openNewReceipt() {
        receipt = Receipt(0F, 0F, false)
        doAsync {
            val id = try {
                db.receiptDao().insert(receipt!!)
            } catch (e: Exception) {
                null
            }

            uiThread {
                id?.let {
                    receipt!!.id = id
                    updateUIOnReceiptReady()
                } ?: run {
                    setResult(RESULT_CODE_FAILED)
                    finish()
                }
            }
        }
    }

    private fun updateUIOnReceiptReady() {
        title = String.format(getString(R.string.receipt_nr), receipt!!.id)
        getItems()
        setupFooter();
    }

    internal fun setupAdapter(adapter: ItemAdapter) {
        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.setLayoutManager(mLayoutManager)
        recycler_view.setItemAnimator(DefaultItemAnimator())
        recycler_view.setAdapter(adapter)
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

    private fun deleteEmptyReceipt(){
        val rowsLiveData = receiptRowPresenter.getRowsByReceipt(receipt!!.id)
        rowsLiveData.observe(this, Observer { rows ->
            takeIf { rows!!.size < 1 }?.apply { doAsync { db.receiptDao().delete(receipt!!) } }
        })
    }

    override fun resolveDaggerDependency() {
        DaggerNewReceiptComponent.builder()
                .applicationComponent(getApplicationComponent())
                .newReceiptModule(NewReceiptModule(this))
                .build().inject(this)
    }

    override fun getContentView(): Int = R.layout.activity_new_receipt
}
