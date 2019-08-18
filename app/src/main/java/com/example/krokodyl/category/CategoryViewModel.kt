package com.example.krokodyl.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.krokodyl.Repository.Repository
import com.example.krokodyl.model.DatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CategoryViewModel (val database: DatabaseDao, application: Application
) : AndroidViewModel(application){
    var currentCategory = MutableLiveData<String> ()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    var repository: Repository =
        Repository(database, uiScope)

    var categoriesList = repository.getAll()

    fun eventStartGame(categoryId: String) {
        currentCategory.value = categoryId
    }


        fun navigationFinished(){
       currentCategory.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

//
//    init {
//    getAll()
//}
//
//    fun getAll(){
//        uiScope.launch {
//            getAllCategoryFromDatabase()
//
//        }
//    }
//
//    private suspend fun getAllCategoryFromDatabase(){
//        return withContext(Dispatchers.IO) {
//
//                database.update(Category("1", "Змішана", "", listOf<String>("ololo", "alala")))
//
//                database.update(Category("2", "Їжа", "", listOf<String>("ololo", "dfdfdfdf","alala")))
//
//                database.update(Category("3", "Тварини"))
//
//                database.update(Category("4", "Ігри"))
//
//                database.update(Category("5", "Українські Відомі Особістості"))
//
//                database.update(Category("6", "Природа"))
//
//                database.update(Category("7", "Бренди"))
//
//                database.update(Category("8", "Всередині Будинку"))
//
//                database.update(Category("9", "Прилади"))
//
//                database.update(Category("10", "Дії"))
//
//                database.update(Category("11", "Одяг"))
//
//                database.update(Category("12", "Підводний світ"))
//
//
//
//        }}

}
