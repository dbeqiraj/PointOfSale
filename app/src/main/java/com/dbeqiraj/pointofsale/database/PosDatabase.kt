package com.dbeqiraj.pointofsale.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dbeqiraj.pointofsale.database.dao.CategoryDao
import com.dbeqiraj.pointofsale.database.dao.ItemDao
import com.dbeqiraj.pointofsale.database.dao.ReceiptDao
import com.dbeqiraj.pointofsale.database.dao.ReceiptRowDao
import com.dbeqiraj.pointofsale.database.entity.Category
import com.dbeqiraj.pointofsale.database.entity.Item
import com.dbeqiraj.pointofsale.database.entity.Receipt
import com.dbeqiraj.pointofsale.database.entity.ReceiptRow

@Database(
        entities = [
            (Category::class),
            (Item::class),
            (Receipt::class),
            (ReceiptRow::class)
        ],
        version = 1,
        exportSchema = false
)
abstract class PosDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun receiptRowDao(): ReceiptRowDao
}