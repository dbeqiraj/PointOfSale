package com.dbeqiraj.pointofsale.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dbeqiraj.pointofsale.database.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category WHERE category_id = 0 UNION ALL SELECT * FROM (SELECT * FROM category WHERE category_id > 0 ORDER BY LOWER(name))")
    fun getCategories(): LiveData<MutableList<Category>>

    @Query("SELECT * FROM category WHERE name = :name")
    fun getCategoryName(name: String): LiveData<Category>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(category: Category): Long
}