package com.dbeqiraj.pointofsale.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.dbeqiraj.pointofsale.database.entity.ItemAndReceiptRow
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow

@Dao
interface ReceiptRowDao {
    @Query("SELECT * FROM receipt_row WHERE rec_row_receipt_id = :id")
    fun getRowsByReceipt(id: Long): LiveData<MutableList<ReceiptRow>>

    @Query("SELECT * FROM receipt_row WHERE receipt_row_id = :id")
    fun getRowById(id: Long): LiveData<ReceiptRow>

    @Query("SELECT * FROM receipt_row WHERE rec_row_receipt_id = :receipt_id AND rec_row_item_id = :item_id")
    fun getRowByReceiptAndItem(receipt_id: Long, item_id: Long): ReceiptRow?

    @Query("SELECT * " +
            "FROM receipt_row " +
            "INNER JOIN item ON item.item_id = receipt_row.rec_row_item_id " +
            "WHERE receipt_row.rec_row_receipt_id = :receiptId " +
            "ORDER BY LOWER(name)")
    fun getItemsAndRowsByReceipt(receiptId: Long): LiveData<MutableList<ItemAndReceiptRow>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(receiptRow: ReceiptRow): Long

    @Query("UPDATE receipt_row " +
            "SET total_price = ((amount * unit_price) + CASE  WHEN has_tax THEN (amount * unit_price) * rec_row_tax/100 ELSE 0 END) " +
            "WHERE receipt_row_id = :id")
    fun updateTotal(id: Long)

    @Update
    fun update(receiptRow: ReceiptRow): Int

    @Delete
    fun delete(receiptRow: ReceiptRow): Int
}