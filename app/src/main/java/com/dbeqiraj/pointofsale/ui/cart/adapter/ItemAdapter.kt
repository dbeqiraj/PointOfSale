package com.dbeqiraj.pointofsale.ui.cart.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnAddOrRemoveItem
import com.dbeqiraj.pointofsale.ui.cart.interfaces.OnItemViewClick
import com.dbeqiraj.pointofsale.utilities.NumberUtils
import kotlinx.android.synthetic.main.list_pick_items_child.view.*

class ItemAdapter constructor(layoutInflater: LayoutInflater, onAddOrRemoveItem: OnAddOrRemoveItem, onItemViewClick: OnItemViewClick) :
        RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private val mOnAddOrRemoveItem: OnAddOrRemoveItem = onAddOrRemoveItem
    private val mOnItemViewClick: OnItemViewClick = onItemViewClick
    private val mLayoutInflater: LayoutInflater = layoutInflater

    val mItemsAndReceiptRowsList: MutableList<ItemAndReceiptRow> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder(mLayoutInflater.inflate(R.layout.list_pick_items_child, parent, false))

    override fun getItemCount(): Int = mItemsAndReceiptRowsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemAndReceiptRow = mItemsAndReceiptRowsList[position]

        holder.name.text = itemAndReceiptRow.item.name
        if (itemAndReceiptRow.isReceiptRowInitialized() ) {
            holder.price.text = NumberUtils.formatPrice(itemAndReceiptRow.receiptRow.unitPrice)
            holder.amount.text = NumberUtils.formatAmount(itemAndReceiptRow.receiptRow.amount)
        } else {
            holder.price.text = NumberUtils.formatPrice(itemAndReceiptRow.item.price)
            holder.amount.text = "0"
        }

        holder.add.setOnClickListener(onAddItemClick(itemAndReceiptRow))
        holder.remove.setOnClickListener(onRemoveItemClick(itemAndReceiptRow))
        holder.itemView.setOnClickListener(onItemViewClick(itemAndReceiptRow))
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.name
        val price = view.price
        val amount = view.amount
        val add = view.add
        val remove = view.remove
        val item_layout = view.item_layout
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
}