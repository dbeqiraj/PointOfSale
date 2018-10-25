package com.dbeqiraj.pointofsale.ui.new_receipt.interfaces

import com.dbeqiraj.pointofsale.database.entity.Receipt

interface OnReceiptClicked {
    fun receiptClicked(receipt: Receipt)
}