package com.example.krokodyl.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryFromAPI (
    val id : String,
   @Json(name = "name")
   val title : String,
   val image : String,
    val list : List<String>

){

}