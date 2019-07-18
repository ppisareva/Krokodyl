package com.example.krokodyl.category

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.krokodyl.model.Category

    @BindingAdapter("categoryTitle")
    fun TextView.setCategoryTitle(item: Category?) {
        item?.let {
            text = it.categoryTitle
        }
    }


