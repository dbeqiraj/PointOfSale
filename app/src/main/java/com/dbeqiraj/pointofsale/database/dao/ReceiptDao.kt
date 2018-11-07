package com.dbeqiraj.pointofsale.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.dbeqiraj.pointofsale.database.entity.Receipt

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM receipt WHERE receipt_id = :id AND saved = 0")
    fun getCurrentSale(id: Long): LiveData<Receipt>

    @Query("SELECT * FROM receipt WHERE saved = 0")
    fun getUnsavedReceipts(): LiveData<MutableList<Receipt>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(receipt: Receipt): Long

    @Query("UPDATE receipt " +
            "SET total = COALESCE((SELECT " +
            "SUM((amount * unit_price) + CASE  WHEN has_tax THEN (amount * unit_price) * rec_row_tax/100 ELSE 0 END ) as total " +
            "FROM receipt_row " +
            "WHERE rec_row_receipt_id = :id), 0) " +
            "WHERE receipt_id = :id")
    fun updateTotal(id: Long)

    @Update
    fun update(receipt: Receipt): Int

    @Delete
    fun delete(receipt: Receipt): Int
}