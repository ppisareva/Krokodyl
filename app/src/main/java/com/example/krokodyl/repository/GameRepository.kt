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
                    categoryDAO.insert(toDatabaseObject(category))

                }
                return GOOD_RESPONSE
            } catch (e: Exception) {
                "Failure: ${e.message}"
                return BAD_RESPONSE
            }

        }


    }


