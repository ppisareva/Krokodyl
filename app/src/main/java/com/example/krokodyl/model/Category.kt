package com.example.krokodyl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category
    ( @PrimaryKey(autoGenerate = false)
      var categoryId:String="",
      @ColumnInfo(name = "category_title")
      var categoryTitle: String = "",
      @ColumnInfo(name = "category_image")
      var categoryImage:String = "",
      @ColumnInfo(name = "words_list")
      var wordsList:List<String> = listOf())
{



}
