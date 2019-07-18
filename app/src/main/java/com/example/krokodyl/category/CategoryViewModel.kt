package com.example.krokodyl.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krokodyl.model.Category

class CategoryViewModel : ViewModel(){
    // Create a LiveData with a Boolean

    var currentCategory = MutableLiveData<Int> ()
    val categoriesList : MutableLiveData<List<Category>> = MutableLiveData()





    init {
        categoriesList.value = listOf<Category>(Category(1, "ololo", ""))

    }


    fun eventStartGame(categoryId: Int) {
       currentCategory.value = categoryId
    }

    fun navigationFinished(){
       currentCategory.value = null
    }


}
