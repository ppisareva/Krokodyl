package com.example.krokodyl.Repository

import androidx.lifecycle.LiveData
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.DatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    val categoryDAO: DatabaseDao,
    val uiScope: CoroutineScope ){

   fun getAll(): LiveData<List<Category>> {
       getAllFromAPI()
     return categoryDAO.getAll()
   }

    private fun  getAllFromAPI(){


    }

    suspend fun getCategoryByID(categoryId:String): Category {

      return withContext(Dispatchers.IO){
          categoryDAO.getCategoryByID(categoryId)
      }
       }



}