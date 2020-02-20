package com.example.krokodyl.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.KrokodylDatabase
import com.example.krokodyl.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CategoryViewModel ( application: Application
) : AndroidViewModel(application){
    var currentCategory = MutableLiveData<Category> ()

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope( Dispatchers.Main+viewModelJob)
    val database = KrokodylDatabase.getInstance(application).categoryDatabaseDao
    var repository: CategoryRepository = CategoryRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshCategory()
        }
    }

    var categoriesList = repository.getAll()

    fun eventStartGame(category: Category) {
        currentCategory.value = category
    }


        fun navigationFinished(){
       currentCategory.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
