package com.example.krokodyl.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.Repository.Repository
import com.example.krokodyl.model.DatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameViewModel(categoryId : String, val database: DatabaseDao,
                    application: Application) : AndroidViewModel(application){

    val eventGameFinish= MutableLiveData<Boolean>()
    private var currentTimeSeconds = MutableLiveData<Long>()
    var score  = MutableLiveData<Int>()
    var currentWord = MutableLiveData<String>()

    private var index = 0
    lateinit var  wordList : List<String>
    private lateinit var timer : CountDownTimer

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    var repository: Repository = Repository(database, uiScope)





    // changing data to data format to bind it in the xml file
     val currentTimeString = Transformations.map(currentTimeSeconds) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        getWordList(categoryId)
        startTimer()

    }


     fun getWordList(categoryId: String) {
        uiScope.launch(Dispatchers.Main) {
            wordList = repository.getCategoryByID(categoryId).wordsList
            startGame()
        }
    }




    private fun startTimer(){
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




    private fun startGame(){
        score.value = 0
        eventGameFinish.value = false
        wordList.shuffled()
        currentWord.value = wordList.get(index++)

    }


    fun onNextWord(){
        score.value = score.value!!.plus(1)
      if(wordList.size>index){
          currentWord.value=wordList.get(index++)
      } else {
          resetList()
      }

    }
   fun onSkipWord(){
        if(wordList.size>index)
            currentWord.value = wordList[index++]
        else resetList()
    }


    private fun resetList() {
        index = 0
        wordList.shuffled()
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
