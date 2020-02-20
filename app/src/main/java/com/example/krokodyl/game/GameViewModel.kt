package com.example.krokodyl.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.KrokodylDatabase
import com.example.krokodyl.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(category : Category, application: Application) : AndroidViewModel(application){

    val eventGameFinish= MutableLiveData<Boolean>()

    private var currentTimeSeconds = MutableLiveData<Long>()
    var score  = MutableLiveData<Int>()
    var currentWord = MutableLiveData<String>()
    private lateinit var listOfWords: List<String>


    private lateinit var timer : CountDownTimer
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(  viewModelJob + Dispatchers.Main)
    private val database = KrokodylDatabase.getInstance(application).categoryDatabaseDao
    var repository: GameRepository = GameRepository(database, category)

    var  currentCategory = repository.getCategory()
    // changing data to data format to bind it in the xml file
    val currentTimeString = Transformations.map(currentTimeSeconds) { time ->
        DateUtils.formatElapsedTime(time)
    }


    init {
        uiScope.launch {
            repository.updateWordsListByCategoryID(category)
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




    fun startGame(){
        startTimer()
        listOfWords = currentCategory.value!!.listOfWordsCategory

        score.value = 0
        eventGameFinish.value = false
        nextWord()

    }

    private fun nextWord(){
        currentWord.value = listOfWords.get(Random.nextInt(0, listOfWords.size))
    }


    fun onNextWord(){
        score.value = score.value!!.plus(1)
        nextWord()


    }
    fun onSkipWord(){
        nextWord()
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
