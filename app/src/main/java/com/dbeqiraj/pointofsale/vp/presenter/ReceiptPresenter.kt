package com.dbeqiraj.pointofsale.vp.presenter

import android.arch.lifecycle.LiveData
import com.dbeqiraj.pointofsale.base.BasePresenter
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.vp.view.CartView
import javax.inject.Inject

class ReceiptPresenter @Inject constructor() : BasePresenter<CartView>() {

    @Inject
    protected lateinit var db: PosDatabase

    fun getCurrentSale(id: Long): LiveData<Receipt> = db.receiptDao().getCurrentSale(id)

    fun getUnsavedReceipts(): LiveData<MutableList<Receipt>> = db.receiptDao().getUnsavedReceipts()
}