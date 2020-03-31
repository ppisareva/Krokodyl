package com.example.krokodyl.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.Words

class GameViewModelFactory(private val category: Category, private val application: Application, private val wordsList: Words) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(category,  application, wordsList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}