package com.example.krokodyl

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.krokodyl.databinding.GameFragmentBinding


class GameFragment : Fragment()  {

    private lateinit var viewModel: GameViewModel
    private lateinit var  binding : GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // todo pass category id to view model
        val categoryId = GameFragmentArgs.fromBundle(arguments!!).categoryID
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        //set onclick
        binding.nextButton.setOnClickListener {view ->
            viewModel.onNextWord()
        }
        binding.skipButton.setOnClickListener { view ->
            viewModel.onSkipWord()
        }
       // register observers
        viewModel.currentWord.observe(this, Observer { currentWord ->
            binding.guessWordTv.text = currentWord
        })
        viewModel.score.observe(this, Observer { score->
            binding.wordsGuessed.text = score.toString()
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
