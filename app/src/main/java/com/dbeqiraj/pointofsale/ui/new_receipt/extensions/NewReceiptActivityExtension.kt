package com.dbeqiraj.pointofsale.ui.new_receipt.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow
import com.dbeqiraj.pointofsale.ui.new_receipt.NewReceiptActivity
import com.dbeqiraj.pointofsale.ui.new_receipt.adapter.ItemAdapter
import com.dbeqiraj.pointofsale.utilities.Constants
import kotlinx.android.synthetic.main.content_new_receipt.*
import kotlinx.android.synthetic.main.footer.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

internal fun NewReceiptActivity.getReceipt() {
    takeIf { intent.hasExtra("receipt") }?.apply { receipt = intent.getSerializableExtra("receipt") as Receipt }

    if (receipt != null)
        updateUIOnReceiptReady()
    else
        openNewReceipt()
}

internal fun NewReceiptActivity.getItems() {
    val adapter = ItemAdapter(LayoutInflater.from(this))
    setupAdapter(adapter)
    val itemsAndReceiptRowLiveData = receiptRowPresenter.getItemsAndRowsByReceipt(receipt!!.id)
    itemsAndReceiptRowLiveData.observe(this, Observer { itemsAndReceiptRows ->
        adapter.mItemsList.clear()
        adapter.mItemsList.addAll(itemsAndReceiptRows!!)
        adapter.notifyDataSetChanged()
    })
}

internal fun NewReceiptActivity.setupFooter() {
    val currentSale: LiveData<Receipt> = receiptPresenter.getCurrentSale(receipt!!.id)
    currentSale.observe(this, Observer { receipt ->
        if (receipt != null) {
            total_price.text = String.format(getString(R.string.total_money), receipt.total.toString())
        }
    })
    val receiptRowsLiveData: LiveData<MutableList<ReceiptRow>> = receiptRowPresenter.getRowsByReceipt(receipt!!.id)
    receiptRowsLiveData.observe(this, Observer { receiptRows ->
        items_count.text = String.format(getString(R.string.items_count), receiptRows!!.size.toString())
    })
}

internal fun NewReceiptActivity.openNewReceipt() {
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
                setResult(Constants.RESULT_CODE_FAILED)
                finish()
            }
        }
    }
}

internal fun NewReceiptActivity.setupAdapter(adapter: ItemAdapter) {
    val mLayoutManager = LinearLayoutManager(this)
    recycler_view.setLayoutManager(mLayoutManager)
    recycler_view.setItemAnimator(DefaultItemAnimator())
    recycler_view.setAdapter(adapter)
}

internal fun NewReceiptActivity.deleteEmptyReceipt() {
    val rowsLiveData = receiptRowPresenter.getRowsByReceipt(receipt!!.id)
    rowsLiveData.observe(this, Observer { rows ->
        takeIf { rows!!.size < 1 }?.apply { doAsync { db.receiptDao().delete(receipt!!) } }
    })
}