package com.dbeqiraj.pointofsale.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "category", indices = [(Index(value = ["name"], unique = true))])
class Category(
        @NonNull
        @ColumnInfo(name = "name")
        val name: String
) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    var id: Long = 0
}