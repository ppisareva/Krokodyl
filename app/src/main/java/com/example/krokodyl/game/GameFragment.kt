package com.example.krokodyl.game

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.krokodyl.R
import com.example.krokodyl.databinding.GameFragmentBinding
import com.example.krokodyl.model.Category
import com.example.krokodyl.repository.GameRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.game_fragment.*


class GameFragment : Fragment()  {

    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var  binding : GameFragmentBinding
    private lateinit var category :Category
    private lateinit var mp: MediaPlayer
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // binding data
        binding  = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        // get category selected from category fragment
        category = GameFragmentArgs.fromBundle(arguments!!).category
        Log.i("category Id ---", category.idCategory)
        mp = MediaPlayer.create(context, R.raw.ending)

        mp.setOnCompletionListener {
            Navigation.findNavController(view!!)
                .navigate(
                    GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value?:0, category)
                ) }
        return binding.root
    }


    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var isListEmpty = true
        var isFirstLoad = true
        var  application = checkNotNull(this.activity).application
        viewModelFactory = GameViewModelFactory(category, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this
        (activity as AppCompatActivity).toolbar?.title = category.nameCategory
        setHasOptionsMenu(true)

        viewModel.currentCategory.observe(this, Observer {it ->
           it?.let {
               if (!it.listOfWordsCategory.isEmpty()&&isFirstLoad) {
                   viewModel.startReadyTimer()
                   isListEmpty= false
                   isFirstLoad=false
               }
           }

        })

        viewModel.changeColor.observe(this, Observer { it ->
            it?.let{
               if(it<=5) {
                   timer_tv.setTextColor(Color.RED)
               }
                if(it==0L) mp.start()
            }
        })

        viewModel.networkResult.observe(this, Observer { it->
            if(it == GameRepository.BAD_RESPONSE&&isListEmpty){
                viewModel.currentWord.value = getString(R.string.no_internet)
            }
        })

        viewModel.eventStartTimer.observe(this, Observer { isStarted ->
            if(!isStarted){
                skip_button.visibility =  View.VISIBLE
                next_button.visibility = View.VISIBLE
                score.visibility = View.VISIBLE
                timer_tv.visibility = View.VISIBLE
                time_tv.visibility = View.VISIBLE
            }
        })

        viewModel.attentionState.observe(this, Observer { state->
            Log.i("color", "$state")
            var color = 0
            when(state){
                GameViewModel.READY ->{
                    color = R.color.ready
                }
                GameViewModel.GO ->{
                    color = R.color.go
                }
                GameViewModel.GAME ->{
                    color = android.R.color.transparent
                }
                else -> {
                    color = android.R.color.transparent
                }

            }
            gameFragment_layout.setBackgroundColor(resources.getColor(color))
        })

        viewModel.eventGameFinish.observe(this, Observer { isFinished ->
            if(isFinished){
                viewModel.gameFinished()
               skip_button.visibility = View.INVISIBLE
                next_button.visibility =View.INVISIBLE
            }
        })

    }


}


