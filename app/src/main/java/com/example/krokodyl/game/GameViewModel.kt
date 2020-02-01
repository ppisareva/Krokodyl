package com.example.krokodyl.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.DatabaseCategory
import com.example.krokodyl.model.KrokodylDatabase
import com.example.krokodyl.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class GameViewModel(category : Category, application: Application) : AndroidViewModel(application){

    val eventGameFinish= MutableLiveData<Boolean>()

    private var currentTimeSeconds = MutableLiveData<Long>()
    var score  = MutableLiveData<Int>()
    var currentWord = MutableLiveData<String>()
    var  category = MutableLiveData<DatabaseCategory>()

    private var index = 0
    private lateinit var timer : CountDownTimer

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    private val database = KrokodylDatabase.getInstance(application).categoryDatabaseDao

    var repository: Repository = Repository(database)


    lateinit var wordsInGame :List<String>





    // changing data to data format to bind it in the xml file
     val currentTimeString = Transformations.map(currentTimeSeconds) { time ->
        DateUtils.formatElapsedTime(time)
    }

//    init {
//        Log.i("init " ," Game Fragment ")
//        uiScope.launch {
//            withContext(Dispatchers.IO) {
//               val updated =  database.insert(DatabaseCategory("1", "cat", "...", listOf("qwqwe", "osnd", "jksndf")))
//                Log.i("CategoryList", " Updated at position 1, with words ....")
//                val category = database.getCategoryByID("1")
//
//                //repository.updateWordsListByCategoryID(categoryId)
//            }
//        }
//       // category.value =repository.getCategoryByID(categoryId).value
//
//
//
//
//    }







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
        score.value = 0
        eventGameFinish.value = false
        wordsInGame = category.value?.words!!
         Log.i("list of words:", "${category.value!!.words}")
        wordsInGame.shuffled()
        currentWord.value = wordsInGame.get(index++)

    }


    fun onNextWord(){
        score.value = score.value!!.plus(1)
      if(wordsInGame.size>index){
          currentWord.value=wordsInGame.get(index++)
      } else {
          resetList()
      }

    }
   fun onSkipWord(){
        if(wordsInGame.size>index)
            currentWord.value = wordsInGame[index++]
        else resetList()
    }


    private fun resetList() {
        index = 0
        wordsInGame.shuffled()
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
