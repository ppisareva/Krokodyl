package com.example.krokodyl.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel(categoryId : Int) : ViewModel() {

     val eventGameFinish= MutableLiveData<Boolean>()
     private var currentTimeSeconds = MutableLiveData<Long>()
     private var categoryId: Int
     private var currentWordIndex:Int = 0
    // changing data to data format to bind it in the xml file
     val currentTimeString = Transformations.map(currentTimeSeconds) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private lateinit var wordList: MutableList<String>
    private  var timer : CountDownTimer
    var score  = MutableLiveData<Int>()
     var currentWord = MutableLiveData<String>()


    init {
        this@GameViewModel.categoryId = categoryId
        startGame(categoryId)
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                currentTimeSeconds.value = millisUntilFinished/ ONE_SECOND
            }
            override fun onFinish() {
                eventGameFinish.value = true
            }
        }
        timer.start()
    }

    private fun startGame(id:Int){
        resetList(id)
        score.value = 0
        eventGameFinish.value = false
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

    fun gameFinished() {
        eventGameFinish.value = false
    }

    companion object {
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }
}
