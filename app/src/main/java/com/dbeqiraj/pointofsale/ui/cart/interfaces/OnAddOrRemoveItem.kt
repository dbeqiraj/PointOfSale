package com.dbeqiraj.pointofsale.ui.cart.interfaces

import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow

interface OnAddOrRemoveItem {
    fun onAdd(itemAndReceiptRow: ItemAndReceiptRow)
    fun onRemove(itemAndReceiptRow: ItemAndReceiptRow)
}