package com.dbeqiraj.pointofsale.vp.presenter

import android.arch.lifecycle.LiveData
import com.dbeqiraj.pointofsale.base.BasePresenter
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Item
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.vp.view.CartView
import javax.inject.Inject

class ItemPresenter @Inject constructor() : BasePresenter<CartView>() {

    @Inject
    protected lateinit var db: PosDatabase

    fun getItems(): LiveData<MutableList<Item>> = db.itemDao().getItems()

    fun getItemsByCategory(categoryId: Long): LiveData<MutableList<Item>> = db.itemDao().getItemsByCategory(categoryId)

    fun getItemsAndRowsByReceipt(receiptId: Long): LiveData<MutableList<ItemAndReceiptRow>> = db.itemDao().getItemsAndRowsByReceipt(receiptId)

    fun getItemsAndRowsByReceiptAndCategory(receiptId: Long, categoryId: Long): LiveData<MutableList<ItemAndReceiptRow>> = db.itemDao().getItemsAndRowsByReceiptAndCategory(receiptId, categoryId)

}