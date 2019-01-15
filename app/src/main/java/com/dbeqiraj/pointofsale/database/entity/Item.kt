package com.dbeqiraj.pointofsale.database.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.support.annotation.NonNull
import com.dbeqiraj.pointofsale.base.BaseEntity

@Entity(tableName = "item",
        foreignKeys = [(ForeignKey(
                entity = Category::class,
                parentColumns = arrayOf("category_id"),
                childColumns = arrayOf("item_category_id"),
                onDelete = CASCADE))
        ],
        indices = [Index("item_category_id")])
class Item(
        @NonNull
        @ColumnInfo(name = "name")
        val name: String,

        @NonNull
        @ColumnInfo(name = "price")
        val price: Float,

        @ColumnInfo(name = "item_tax")
        val tax: Float?,

        @ColumnInfo(name = "cost")
        val cost: Float?,

        @NonNull
        @ColumnInfo(name = "barcode")
        val barcode: String,

        @NonNull
        @ColumnInfo(name = "item_category_id")
        val categoryId: Long
) : BaseEntity() {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    var id: Long = 0
}