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


fun toCategoryPropertyObject(category: DatabaseCategory):Category {
    return  Category(category.categoryId,category.title, category.image, category.words )
}
// need to pass list of words within fragments
@Parcelize
data class Word(var word: String, var gussedStatus : Boolean ) : Parcelable{}

@Parcelize
data class Words ( var wordsList : MutableList<Word>, var remainedListOfIndexes: MutableList<Int>): Parcelable{}
