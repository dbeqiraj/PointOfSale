package com.dbeqiraj.pointofsale.database.helper

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.dbeqiraj.pointofsale.R

class PosDatabaseHelper(context: Context) {

    companion object {
        val DEFAULT_CATEGORY_ID = 0
    }

    val INSERT_CATEGORY_ALL_ITEMS = "INSERT INTO category(category_id, name) VALUES(" + DEFAULT_CATEGORY_ID + ", '" + context.getString(R.string.all_items) + "')"
//    val DELETE_UNSAVED_RECEIPTS = "DELETE FROM receipt WHERE saved = 0"
//    val CREATE_CURRENT_SALE = "INSERT INTO receipt(total, tax, saved, created_on) VALUES(0, 0, 0, datetime())"

    val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            db.execSQL(INSERT_CATEGORY_ALL_ITEMS)
        }
    }
}