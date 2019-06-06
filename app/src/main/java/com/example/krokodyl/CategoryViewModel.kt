package com.example.krokodyl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krokodyl.dummy.DummyContent

class CategoryViewModel : ViewModel(){
    // Create a LiveData with a Boolean

    var categoryID = MutableLiveData<Int> ()

    fun onCategoryClick(item: DummyContent.DummyItem) {
        categoryID.value = item.id

    }

}
