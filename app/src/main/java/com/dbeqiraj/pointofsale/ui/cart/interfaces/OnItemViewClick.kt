package com.dbeqiraj.pointofsale.ui.cart.interfaces

import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow

interface OnItemViewClick {
    fun onClick(itemAndReceiptRow: ItemAndReceiptRow)
}