package com.example.krokodyl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class Category(

     var idCategory:String,

     var nameCategory: String,

     var imageCategory:String,

     var listOfWordsCategory:List<String>) :Parcelable {}

fun toDatabaseObject(category: Category): DatabaseCategory{
    return DatabaseCategory(category.idCategory, category.nameCategory, category.imageCategory, category.listOfWordsCategory)

}


