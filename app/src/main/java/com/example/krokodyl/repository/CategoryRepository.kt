package com.example.krokodyl.repository

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
        return categoryList
    }


    // todo transfer categories type
    private fun updateDB(category: List<CategoryFromAPI>) {
        for (category in category) {
            categoryDAO.insert(
                DatabaseCategory(
                    categoryId = category.id,
                    title = category.title,
                    image = category.image

                )
            )
        }
    }

    suspend fun refreshCategory() {
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

