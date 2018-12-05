package com.dbeqiraj.pointofsale.ui.home.adapter

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.ui.new_receipt.interfaces.OnReceiptClicked
import com.dbeqiraj.pointofsale.utilities.Constants
import com.dbeqiraj.pointofsale.utilities.DateUtils
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import com.dbeqiraj.pointofsale.utilities.random
import kotlinx.android.synthetic.main.list_receipt_child.view.*
import java.util.*

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
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        holder.icon.background = getIconColor()
        holder.title.text = String.format(mContext.getString(R.string.receipt_nr), receipt.id)
        holder.date.text = DateUtils.getHumanReadableDate(receipt.createdOn)
        holder.total.text = NumberUtils.formatPrice(receipt.total)
        holder.itemView.setOnClickListener(onReceiptClicked(receipt))
    }

    private fun onReceiptClicked(receipt: Receipt) = View.OnClickListener {
        mOnReceiptClicked.receiptClicked(receipt)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.icon
        val title = view.title
        val date = view.date
        val total = view.total
    }

    private fun getIconColor(): Drawable? {
        val circle = ContextCompat.getDrawable(mContext, R.drawable.circle)
        circle!!.setColorFilter(Color.parseColor(Constants.colorsList.random()), PorterDuff.Mode.SRC_ATOP)
        return circle
    }
}