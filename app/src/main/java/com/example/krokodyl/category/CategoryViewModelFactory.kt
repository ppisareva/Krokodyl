package com.example.krokodyl.category

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.krokodyl.model.DatabaseDao


class CategoryViewModelFactory(private val database: DatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}