package com.dbeqiraj.pointofsale.ui.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.ui.new_receipt.interfaces.OnReceiptClicked
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import kotlinx.android.synthetic.main.list_receipt_child.view.*

class ReceiptsAdapter constructor(layoutInflater: LayoutInflater, onReceiptClicked: OnReceiptClicked) : RecyclerView.Adapter<ReceiptsAdapter.MyViewHolder>() {

    private lateinit var mContext: Context
    private val mLayoutInflater: LayoutInflater = layoutInflater
    private val mOnReceiptClicked: OnReceiptClicked = onReceiptClicked

    val mReceiptsList: MutableList<Receipt> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(mLayoutInflater.inflate(R.layout.list_receipt_child, parent, false))
    }

    override fun getItemCount(): Int = mReceiptsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val receipt = mReceiptsList[position]
        holder.title.text = String.format(mContext.getString(R.string.receipt_nr), receipt.id)
        holder.date.text = receipt.createdOn.toString()
        holder.total.text = NumberUtils.formatNumber(receipt.total)
        holder.itemView.setOnClickListener(onReceiptClicked(receipt))
    }

    private fun onReceiptClicked(receipt: Receipt) = View.OnClickListener {
        mOnReceiptClicked.receiptClicked(receipt)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.title
        val date = view.date
        val total = view.total
    }
}