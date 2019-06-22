package com.example.krokodyl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

   lateinit var wordList: MutableList<String>
    var score  = MutableLiveData<Int>()
    var currentWordIndex:Int = 0
     var currentWord = MutableLiveData<String>()

    init {
        score.value = 0
        // todo
        startGame(1)

    }

    fun startGame(id:Int){
        resetList(id)
        currentWord.value = wordList[currentWordIndex]
    }

  fun onNextWord(){

      if(currentWordIndex<wordList.size-1){
          currentWord.value = wordList[++currentWordIndex]
          score.value = score.value!!.plus(1)
      } else {
          resetList(0)
      }

    }
    fun onSkipWord(){
        if(currentWordIndex<wordList.size-1) currentWord.value = wordList[++currentWordIndex]
        else resetList(0)
    }


    private fun resetList(id:Int) {
        currentWordIndex = 0
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        // randomly mixed words
        wordList.shuffle()
    }
}
