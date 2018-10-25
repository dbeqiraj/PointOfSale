package com.dbeqiraj.pointofsale.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dbeqiraj.pointofsale.database.entity.Item
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow

@Dao
interface ItemDao {

    @Query("SELECT * FROM item ORDER BY LOWER(name)")
    fun getItems(): LiveData<MutableList<Item>>

    @Query("SELECT * FROM item WHERE item_category_id = :categoryId ORDER BY LOWER(name)")
    fun getItemsByCategory(categoryId: Long): LiveData<MutableList<Item>>

    @Query("SELECT * " +
            "FROM item " +
            "LEFT JOIN (SELECT * FROM receipt_row WHERE receipt_row.rec_row_receipt_id = :receiptId) as T ON T.rec_row_item_id = item.item_id " +
            "ORDER BY LOWER(name)")
    fun getItemsAndRowsByReceipt(receiptId: Long): LiveData<MutableList<ItemAndReceiptRow>>

    @Query("SELECT * " +
            "FROM item " +
            "LEFT JOIN (SELECT * FROM receipt_row WHERE receipt_row.rec_row_receipt_id = :receiptId) as T ON T.rec_row_item_id = item.item_id " +
            "WHERE item.item_category_id = :categoryId " +
            "ORDER BY LOWER(name)")
    fun getItemsAndRowsByReceiptAndCategory(receiptId: Long, categoryId: Long): LiveData<MutableList<ItemAndReceiptRow>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(item: Item): Long
}