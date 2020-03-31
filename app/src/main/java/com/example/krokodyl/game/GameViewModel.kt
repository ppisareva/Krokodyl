package com.example.krokodyl.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.AppPreferences
import com.example.krokodyl.R
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.KrokodylDatabase
import com.example.krokodyl.model.Word
import com.example.krokodyl.model.Words
import com.example.krokodyl.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(var category : Category, application: Application, var listOfWords:Words) : AndroidViewModel(application){

    val eventGameFinish= MutableLiveData<Boolean>()
    val eventStartTimer = MutableLiveData<Boolean>()
    val networkResult = MutableLiveData<Int>()
    val attentionState = MutableLiveData<Int>()
    val changeColor = MutableLiveData<Long>()

    private var currentTimeSeconds = MutableLiveData<Long>()
    var score  = MutableLiveData<Int>()
    var currentWord = MutableLiveData<String>()
    private lateinit var words: Words
    private lateinit var listofAllWords: List<String>



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

    fun getWords ():Words{
        return words
    }

    init {
        uiScope.launch {
            networkResult.value =  repository.updateWordsListByCategoryID(category)
            Log.e("result", "${networkResult.value}")
        }
    }

     fun getWordsFromFile(){
        uiScope.launch {
            repository.getFromFile(getApplication(), category)
        }


    }



     fun startReadyTimer(){
         eventStartTimer.value = true
         timer = object : CountDownTimer(START_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                 when (millisUntilFinished/ ONE_SECOND) {
                    ONE -> {
                        currentWord.value =  getApplication<Application>().applicationContext.getString(R.string.ready)
                        attentionState.value = READY
                        Log.e("status2", "${attentionState.value}")
                    }
                     TWO-> {
                         currentWord.value =  getApplication<Application>().applicationContext.getString(R.string.ready)
                         attentionState.value = READY
                         Log.e("status2", "${attentionState.value}")
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
        Log.e("timer", "${AppPreferences.timer}")
        timer = object : CountDownTimer(AppPreferences.timer, ONE_SECOND)  {
            override fun onTick(millisUntilFinished: Long) {
                currentTimeSeconds.value = millisUntilFinished/ ONE_SECOND
                changeColor.value = millisUntilFinished/ ONE_SECOND
            }
            override fun onFinish() {
                eventGameFinish.value = true
            }
        }
        timer.start()
    }


    fun startGame(){
        startTimer()
        words = listOfWords
        chackIfListOfIndexesNotEmpty()
        listofAllWords = currentCategory.value!!.listOfWordsCategory
        score.value = 0
        eventGameFinish.value = false
        nextWord()
    }

    private fun nextWord(){
        nextIndex().let {
           if(it>=0) currentWord.value = listofAllWords.get(it)
            else {
               currentWord.value = "end of words"
               eventGameFinish.value = true

           }
        }

    }

    fun onNextWord(){
        score.value = score.value!!.plus(1)
        words.wordsList.add(Word(currentWord.value.toString(), true ))
        nextWord()
    }

    fun onSkipWord(){
        words.wordsList.add(Word(currentWord.value.toString(), false ))
        nextWord()
    }

    fun gameFinished() {
        eventGameFinish.value = false
    }

    fun endOfTimer(){
        eventGameFinish.value = true
    }

    fun  chackIfListOfIndexesNotEmpty(){
        if(words.remainedListOfIndexes.isEmpty()){
            val size = currentCategory.value!!.listOfWordsCategory.size
           words.remainedListOfIndexes = MutableList(size-1) { i -> i+1 }
            words.remainedListOfIndexes.shuffle()
            Log.i("remainedListOfIndexes" ,"${words.remainedListOfIndexes.toString()}")

        }
    }

    fun nextIndex():Int{
        if(words.remainedListOfIndexes.size>0){
            return words.remainedListOfIndexes.removeAt(0)
        }
        return -1
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
        const val THREE : Long = 3
        const val READY = 1
        const val GO = 2
        const val GAME = 0
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
