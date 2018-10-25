package com.dbeqiraj.pointofsale.database.entity

import android.arch.persistence.room.Embedded

class ItemAndReceiptRow {
    @Embedded
    lateinit var item: Item

    @Embedded
    lateinit var receiptRow: ReceiptRow

    fun isItemInitialized() = this::item.isInitialized

    fun isReceiptRowInitialized() = this::receiptRow.isInitialized
}