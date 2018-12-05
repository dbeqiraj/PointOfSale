package com.dbeqiraj.pointofsale.database.entity

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.dbeqiraj.pointofsale.utilities.DateUtils
import java.util.*

@Entity(
        tableName = "receipt_row",
        foreignKeys = [
            (ForeignKey(
                    entity = Item::class,
                    parentColumns = arrayOf("item_id"),
                    childColumns = arrayOf("rec_row_item_id"),
                    onDelete = ForeignKey.SET_NULL)),
            (ForeignKey(
                    entity = Receipt::class,
                    parentColumns = arrayOf("receipt_id"),
                    childColumns = arrayOf("rec_row_receipt_id"),
                    onDelete = ForeignKey.CASCADE))
        ],
        indices = [(Index("rec_row_item_id")), (Index("rec_row_receipt_id"))]
)
class ReceiptRow(
        @NonNull
        @ColumnInfo(name = "amount")
        var amount: Float,

        @NonNull
        @ColumnInfo(name = "unit_price")
        var unitPrice: Float,

        @NonNull
        @ColumnInfo(name = "total_price")
        var totalPrice: Float,

        @NonNull
        @ColumnInfo(name = "rec_row_tax")
        val tax: Float,

        @NonNull
        @ColumnInfo(name = "has_tax")
        var hasTax: Boolean,

        @NonNull
        @ColumnInfo(name = "rec_row_item_id")
        val itemId: Long,

        @NonNull
        @ColumnInfo(name = "rec_row_receipt_id")
        val receiptId: Long
) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "receipt_row_id")
    var id: Long = 0

    @NonNull
    @ColumnInfo(name = "created_on")
    @TypeConverters(DateUtils.DateTimeConverter::class)
    var createdOn: Date = Date(System.currentTimeMillis())
}