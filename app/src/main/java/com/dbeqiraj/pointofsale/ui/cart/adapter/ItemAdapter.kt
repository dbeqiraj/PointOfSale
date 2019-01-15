package com.dbeqiraj.pointofsale.ui.cart.adapter

import android.view.LayoutInflater
import android.view.View
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseEntity
import com.dbeqiraj.pointofsale.base.BaseRecyclerViewAdapter
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnAddOrRemoveItem
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnItemViewClick
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import kotlinx.android.synthetic.main.list_pick_items_child.view.*

class ItemAdapter constructor(layoutInflater: LayoutInflater, onAddOrRemoveItem: OnAddOrRemoveItem, onItemViewClick: OnItemViewClick) :
        BaseRecyclerViewAdapter(layoutInflater) {

    private val mOnAddOrRemoveItem: OnAddOrRemoveItem = onAddOrRemoveItem
    private val mOnItemViewClick: OnItemViewClick = onItemViewClick

    override fun fillItemView(view: View, item: BaseEntity) {
        item as ItemAndReceiptRow
        view.name.text = item.item.name
        if (item.isReceiptRowInitialized() ) {
            view.price.text = NumberUtils.formatPrice(item.receiptRow.unitPrice)
            view.amount.text = NumberUtils.formatAmount(item.receiptRow.amount)
        } else {
            view.price.text = NumberUtils.formatPrice(item.item.price)
            view.amount.text = "0"
        }

        view.add.setOnClickListener(onAddItemClick(item))
        view.remove.setOnClickListener(onRemoveItemClick(item))
        view.setOnClickListener(onItemViewClick(item))
    }

    private fun onAddItemClick(itemAndReceiptRow: ItemAndReceiptRow) = View.OnClickListener {
        mOnAddOrRemoveItem.onAdd(itemAndReceiptRow)
    }

    private fun onRemoveItemClick(itemAndReceiptRow: ItemAndReceiptRow) = View.OnClickListener {
        mOnAddOrRemoveItem.onRemove(itemAndReceiptRow)
    }

    private fun onItemViewClick(itemAndReceiptRow: ItemAndReceiptRow) = View.OnClickListener {
        mOnItemViewClick.onClick(itemAndReceiptRow)
    }

    override fun getItemContentView(): Int = R.layout.list_pick_items_child

}