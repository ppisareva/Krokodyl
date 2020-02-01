package com.example.krokodyl.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DatabaseDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: DatabaseCategory)


    @Query("SELECT * from category_table")
    fun getAll(): LiveData<List<DatabaseCategory>>


    @Query("UPDATE category_table SET words_list = :wordList WHERE categoryId = :id")
    fun updateCategoryWordsList(id: String, wordList: List<String>): Int

    @Query ("SELECT * FROM category_table where categoryId =:category")
    fun getCategoryByID(category: String) : LiveData<DatabaseCategory>
}
