package com.example.krokodyl.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class ScoreViewModel(scoreOfGame : Int) : ViewModel() {

     val score = MutableLiveData<Int>()
     val eventNewGame  = MutableLiveData<Boolean>()
    val eventToCategory = MutableLiveData<Boolean>()

    init {
        score.value = scoreOfGame
        eventToCategory.value = false
        eventNewGame.value = false
    }

    fun onStartNewGame(){
        eventNewGame. value = true
    }
    fun onEventStartNewGameEnded(){
        eventNewGame.value=false
    }

    fun onReturnToList(){
        eventToCategory.value = true
    }
    fun onReturnToCategoryEventEnded(){
        eventToCategory.value = false
    }

}
