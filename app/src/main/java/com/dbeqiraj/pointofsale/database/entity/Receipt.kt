package com.dbeqiraj.pointofsale.database.entity

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.dbeqiraj.pointofsale.base.BaseEntity
import com.dbeqiraj.pointofsale.utilities.DateUtils
import java.io.Serializable
import java.util.*

@Entity(tableName = "receipt")
class Receipt(
        @NonNull
        @ColumnInfo(name = "total")
        var total: Float,

        @NonNull
        @ColumnInfo(name = "tax")
        val tax: Float,

        @NonNull
        @ColumnInfo(name = "saved")
        val saved: Boolean
) : BaseEntity(), Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "receipt_id")
    var id: Long = 0

    @NonNull
    @ColumnInfo(name = "created_on")
    @TypeConverters(DateUtils.DateTimeConverter::class)
    var createdOn: Date = Date(System.currentTimeMillis())
}