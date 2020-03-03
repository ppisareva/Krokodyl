package com.example.krokodyl.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.model.*
import com.example.krokodyl.network.KrokodylAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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


    suspend fun firstInit() {
       if(categoryDAO.getAll().value.isNullOrEmpty()){
           Log.e("first init", "download from API" )
           getDataFromAPI()
       }
    }

    suspend fun refreshCategory() {
        getDataFromAPI()
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







}

