package com.example.krokodyl.game

import android.content.pm.ActivityInfo
import android.os.Bundle
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


class GameFragment : Fragment()  {

    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var  binding : GameFragmentBinding
    private var categoryId :Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        categoryId = GameFragmentArgs.fromBundle(arguments!!).categoryID
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelFactory = GameViewModelFactory(categoryId)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventGameFinish.observe(this, Observer { isFinished ->
            if(isFinished){
                viewModel.gameFinished()
                Navigation.findNavController(view!!)
                    .navigate(
                        GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value?:0, categoryId)
                    )
            }
        })

    }






     // set orientation for fragment to landScape
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
    // change orientation to Unspecified
    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}
