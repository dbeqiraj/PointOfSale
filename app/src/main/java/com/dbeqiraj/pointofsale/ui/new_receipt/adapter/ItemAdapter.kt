package com.dbeqiraj.pointofsale.ui.new_receipt.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import kotlinx.android.synthetic.main.list_new_receipt_item.view.*

class ItemAdapter constructor(layoutInflater: LayoutInflater) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private lateinit var mContext: Context
    private val mLayoutInflater: LayoutInflater = layoutInflater

    val mItemsAndReceiptRowsList: MutableList<ItemAndReceiptRow> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(mLayoutInflater.inflate(R.layout.list_new_receipt_item, parent, false))
    }

    override fun getItemCount(): Int = mItemsAndReceiptRowsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val row = mItemsAndReceiptRowsList[position]

        holder.name.text = row.item.name
        holder.amount_x_price.text = String.format(mContext.getString(R.string.amount_x_price), NumberUtils.formatAmount(row.receiptRow.amount),  NumberUtils.formatAmount(row.receiptRow.unitPrice))
        holder.total.text = NumberUtils.formatNumber(row.receiptRow.totalPrice)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val delete = view.delete
        val amount_x_price = view.amount_x_price
        val total = view.total
        val name = view.name
    }
}