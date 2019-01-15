package com.dbeqiraj.pointofsale.ui.home.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseEntity
import com.dbeqiraj.pointofsale.base.BaseRecyclerViewAdapter
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.ui.new_receipt.interfaces.OnReceiptClicked
import com.dbeqiraj.pointofsale.utilities.Constants
import com.dbeqiraj.pointofsale.utilities.DateUtils
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import com.dbeqiraj.pointofsale.utilities.random
import kotlinx.android.synthetic.main.list_receipt_child.view.*

class ReceiptsAdapter constructor(layoutInflater: LayoutInflater, onReceiptClicked: OnReceiptClicked) : BaseRecyclerViewAdapter(layoutInflater) {

    private val mOnReceiptClicked: OnReceiptClicked = onReceiptClicked

    override fun fillItemView(view: View, item: BaseEntity) {
        item as Receipt
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        view.icon.background = getIconColor()
        view.title.text = String.format(mContext.getString(R.string.receipt_nr), item.id)
        view.date.text = DateUtils.getHumanReadableDate(item.createdOn, getStringSharedPref(R.string.pref_date_format, "dd/MM/yyyy"))
        view.total.text = NumberUtils.formatPrice(item.total)
        view.setOnClickListener(onReceiptClicked(item))
    }

    private fun onReceiptClicked(receipt: Receipt) = View.OnClickListener {
        mOnReceiptClicked.receiptClicked(receipt)
    }

    private fun getIconColor(): Drawable? {
        val circle = ContextCompat.getDrawable(mContext, R.drawable.circle)
        circle!!.setColorFilter(Color.parseColor(Constants.colorsList.random()), PorterDuff.Mode.SRC_ATOP)
        return circle
    }

    override fun getItemContentView(): Int = R.layout.list_receipt_child


}