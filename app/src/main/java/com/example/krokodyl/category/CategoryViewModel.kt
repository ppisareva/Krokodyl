package com.example.krokodyl.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krokodyl.dummy.DummyContent

class CategoryViewModel : ViewModel(){
    // Create a LiveData with a Boolean

    var categoryID = MutableLiveData<Int> ()

    var onCategoryChooseEvent = MutableLiveData<Boolean>()

    init {
        onCategoryChooseEvent.value = false
        categoryID.value = -1
    }


    fun startNavigationEvent(item: DummyContent.DummyItem) {
        categoryID.value = item.id
        onCategoryChooseEvent.value = true

    }
    fun navigationFinished(){
        onCategoryChooseEvent.value = false
    }


}
