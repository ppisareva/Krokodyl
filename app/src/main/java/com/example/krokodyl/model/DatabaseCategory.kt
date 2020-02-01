package com.example.krokodyl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class DatabaseCategory
    (@PrimaryKey(autoGenerate = false)
      var categoryId:String="",
     @ColumnInfo(name = "category_title")
      var title: String = "",
     @ColumnInfo(name = "category_image")
      var image:String = "",
     @ColumnInfo(name = "words_list")
      var words:List<String> = listOf())


    fun List<DatabaseCategory>.asDomainModel(): List<Category> {
        return map{
            Category(it.categoryId,it.title, it.image, it.words )
        }
    }





