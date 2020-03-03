package com.example.krokodyl.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.krokodyl.R
import com.example.krokodyl.databinding.GameFragmentBinding
import com.example.krokodyl.model.Category
import kotlinx.android.synthetic.main.game_fragment.*

// todo first time start game slow
class GameFragment : Fragment()  {

    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var  binding : GameFragmentBinding
    private lateinit var category :Category

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // binding data
        binding  = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        // get category selected from category fragment
        category = GameFragmentArgs.fromBundle(arguments!!).category
       Log.i("category Id ---", category.idCategory)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var firstStart = true
        var  application = checkNotNull(this.activity).application
        viewModelFactory = GameViewModelFactory(category, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        // todo improve 
        viewModel.currentCategory.observe(this, Observer {it ->
           it?.let {
               if (!it.listOfWordsCategory.isEmpty()&& firstStart) {
                  firstStart = false
                   viewModel.startReadyTimer()
               }
           }

        })

        viewModel.eventStartTimer.observe(this, Observer { isStarted ->
            if(!isStarted){
                skip_button.visibility =  View.VISIBLE
                next_button.visibility = View.VISIBLE
                score.visibility = View.VISIBLE
                timer_tv.visibility = View.VISIBLE
            }
        })

        viewModel.eventGameFinish.observe(this, Observer { isFinished ->
            if(isFinished){
                viewModel.gameFinished()
                Navigation.findNavController(view!!)
                    .navigate(
                        GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value?:0, category)
                    )
            }
        })

    }
}


