package com.example.krokodyl.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.model.*
import com.example.krokodyl.network.KrokodylAPI
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class CategoryRepository(private val categoryDAO: DatabaseDao) {
    // category fragment Database Format to Application Object
    var categoryList: LiveData<List<Category>> =  Transformations.map(categoryDAO.getAll()) {
        it.asDomainModel()
    }

    fun getAll(): LiveData<List<Category>> {
        Log.e("list from DB", "-----"
        )
        return categoryList
    }

    suspend fun firstInit(ap : Application) {
        getFromFile(ap)

    }


    suspend fun refreshCategory() {
        getDataFromAPI()
    }


    suspend fun getFromFile(ap : Application){
        val file_name = "category.txt"
        val json_string = ap.assets.open(file_name).bufferedReader().use{
            it.readText()
        }
        Log.e("file" ,json_string)
        // convert to object
        val listType = Types.newParameterizedType(List::class.java, CategoryFromAPI::class.java)
        val adapter: JsonAdapter<List<CategoryFromAPI>> = KrokodylAPI.getMoshi.adapter(listType)
        val result = adapter.fromJson(json_string)
        Log.e("file" ,result!!.size.toString())
        // read to database
        try{
            withContext(Dispatchers.IO) {
                updateDB(result)
                "Success: ${result.size} Category properties retrieved"
            }
        } catch (e: Exception) {
            "Failure: ${e.message}"
        }

    }

    suspend fun getDataFromAPI (){
        var getPropertiesDeferred = KrokodylAPI.retrofitService.getCategoriesList()
        try {
            var listResult = getPropertiesDeferred.await()
            withContext(Dispatchers.IO) {
                updateDB(listResult)
                "Success: ${listResult.size} Category properties retrieved"
            }
        } catch (e: Exception) {
            "Failure: ${e.message}"
        }
    }

    private fun updateDB(category: List<CategoryFromAPI>) {
        for (c in category) {
            categoryDAO.insert(
                DatabaseCategory(
                    categoryId = c.id,
                    title = c.title,
                    image = c.image
                )
            )
        }
    }
}

