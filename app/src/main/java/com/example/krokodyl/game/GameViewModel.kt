package com.example.krokodyl.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.R
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
    val eventStartTimer = MutableLiveData<Boolean>()
    val networkResult = MutableLiveData<Int>()
    val attentionState = MutableLiveData<Int>()

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
            networkResult.value =  repository.updateWordsListByCategoryID(category)
            Log.e("result", "${networkResult.value}")
        }
    }

     fun startReadyTimer(){
         eventStartTimer.value = true
         Log.e("status1", "${attentionState.value}")
        timer = object : CountDownTimer(START_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                 when (millisUntilFinished/ ONE_SECOND) {
                    ONE-> {
                        currentWord.value =  getApplication<Application>().applicationContext.getString(R.string.ready)
                        attentionState.value = READY
                        Log.e("status2", "${attentionState.value}")
                    }
                    TWO-> {
                        currentWord.value =  getApplication<Application>().applicationContext.getString(R.string.ready)
                        attentionState.value = READY
                        Log.e("status3", "${attentionState.value}")
                    }
                    else -> {
                        currentWord.value =  getApplication<Application>().applicationContext.getString(R.string.go)
                        attentionState.value = GO
                        Log.e("status4", "${attentionState.value}")
                    }
                }
            }
            override fun onFinish() {
                startGame()
                eventStartTimer.value = false
                attentionState.value = GAME
                Log.e("status5", "${attentionState.value}")
            }
        }
        timer.start()
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
        //
        const val START_TIME = 2000L
        const val ONE : Long = 1
        const val TWO : Long = 2
        const val READY = 1
        const val GO = 2
        const val GAME = 0
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
