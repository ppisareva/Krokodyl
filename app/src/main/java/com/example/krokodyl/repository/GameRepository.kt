package com.example.krokodyl.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.model.*
import com.example.krokodyl.network.KrokodylAPI
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(private val categoryDAO: DatabaseDao, val category: Category) {

    companion object{
        const val BAD_RESPONSE= 200
        const val GOOD_RESPONSE =500

    }

    private val currentCategory :LiveData<Category> = Transformations.map(categoryDAO.getCategoryByID(category.idCategory)) {
        toCategoryPropertyObject(it)
    }

    fun getCategory(): LiveData<Category> {
        return currentCategory
    }



    suspend fun updateWordsListByCategoryID(category: Category) : Int{

            // get data from API and update category in DB
            try {
                   category.listOfWordsCategory =
                       KrokodylAPI.retrofitService.getWordsListByCategory(category.idCategory).await()

                withContext(Dispatchers.IO) {

                  updateDB(category)

                }
                return GOOD_RESPONSE
            } catch (e: Exception) {
                "Failure: ${e.message}"
                return BAD_RESPONSE
            }

        }


    suspend fun getFromFile(ap : Application, category: Category){
        val file_name = "category${category.idCategory}.txt"
        val json_string = ap.assets.open(file_name).bufferedReader().use{
            it.readText()
        }
        Log.e("file" ,json_string)
        // convert to object
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = KrokodylAPI.getMoshi.adapter(listType)
        val result = adapter.fromJson(json_string)

        Log.e("file" ,result!!.size.toString())
        // write to database
        try{
            withContext(Dispatchers.IO) {
                category.listOfWordsCategory=result
                updateDB(category)
                "Success: ${result.size} Category properties retrieved"
            }
        } catch (e: Exception) {
            "Failure: ${e.message}"
        }

    }

    private fun updateDB(category: Category) {
        categoryDAO.insert(toDatabaseObject(category))
    }

}


