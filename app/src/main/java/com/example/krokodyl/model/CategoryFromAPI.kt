package com.example.krokodyl.model

import com.squareup.moshi.Json

data class CategoryFromAPI (
    val id : String,
   @Json(name = "name")
   val title : String,
val image : String
){

}