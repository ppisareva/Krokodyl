package com.example.krokodyl.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.krokodyl.model.DatabaseDao

class GameViewModelFactory(private val categoryId: String, private val database: DatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(categoryId, database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}