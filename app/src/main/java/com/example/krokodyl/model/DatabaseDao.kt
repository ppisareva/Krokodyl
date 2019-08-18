package com.example.krokodyl.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface DatabaseDao {
    @Insert
    fun insert(category: Category)

    @Update
    fun update (category: Category)

    @Query("SELECT * from category_table")
    fun getAll(): LiveData<List<Category>>

    @Query("DELETE FROM category_table")
    fun clear()

    @Query ("SELECT * FROM category_table where categoryId =:category")
    fun getCategoryByID(category: String) : Category
}
