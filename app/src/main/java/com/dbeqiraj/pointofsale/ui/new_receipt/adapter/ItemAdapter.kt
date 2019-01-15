package com.dbeqiraj.pointofsale.ui.new_receipt.adapter

import android.view.LayoutInflater
import android.view.View
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseEntity
import com.dbeqiraj.pointofsale.base.BaseRecyclerViewAdapter
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import kotlinx.android.synthetic.main.list_new_receipt_item.view.*

class ItemAdapter constructor(layoutInflater: LayoutInflater) : BaseRecyclerViewAdapter(layoutInflater) {

    override fun fillItemView(view: View, item: BaseEntity) {
        item as ItemAndReceiptRow
        view.name.text = item.item.name
        view.amount_x_price.text = String.format(mContext.getString(R.string.amount_x_price), NumberUtils.formatAmount(item.receiptRow.amount),  NumberUtils.formatPrice(item.receiptRow.unitPrice))
        view.total.text = NumberUtils.formatPrice(item.receiptRow.totalPrice)
    }

    override fun getItemContentView(): Int = R.layout.list_new_receipt_item

}