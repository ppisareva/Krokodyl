package com.example.krokodyl.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.DatabaseDao
import com.example.krokodyl.model.toCategoryPropertyObject
import com.example.krokodyl.model.toDatabaseObject
import com.example.krokodyl.network.KrokodylAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(private val categoryDAO: DatabaseDao, val category: Category) {

    private val currentCategory :LiveData<Category> = Transformations.map(categoryDAO.getCategoryByID(category.idCategory)) {
        toCategoryPropertyObject(it)
    }

    fun getCategory(): LiveData<Category> {
        return currentCategory
    }



    suspend fun updateWordsListByCategoryID(category: Category) {
        withContext(Dispatchers.IO) {
            // get data from API and update category in DB
            category.listOfWordsCategory = KrokodylAPI.retrofitService.getWordsListByCategory(category.idCategory).await()
            categoryDAO.insert(toDatabaseObject(category))
        }


    }
}


