package com.dbeqiraj.pointofsale.vp.presenter

import android.arch.lifecycle.LiveData
import com.dbeqiraj.pointofsale.base.BasePresenter
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow
import com.dbeqiraj.pointofsale.vp.view.CartView
import javax.inject.Inject

class ReceiptRowPresenter @Inject constructor() : BasePresenter<CartView>() {

    @Inject
    protected lateinit var db: PosDatabase

    fun getRowsByReceipt(id: Long): LiveData<MutableList<ReceiptRow>> = db.receiptRowDao().getRowsByReceipt(id)

    fun getRowById(id: Long): LiveData<ReceiptRow> = db.receiptRowDao().getRowById(id)

    fun getItemsAndRowsByReceipt(id: Long): LiveData<MutableList<ItemAndReceiptRow>> = db.receiptRowDao().getItemsAndRowsByReceipt(id)

    fun getRowByReceiptAndItem(receipt_id: Long, item_id: Long): ReceiptRow? = db.receiptRowDao().getRowByReceiptAndItem(receipt_id, item_id)
}