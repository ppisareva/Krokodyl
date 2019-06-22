package com.example.krokodyl

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.krokodyl.databinding.GameFragmentBinding


class GameFragment : Fragment()  {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : GameFragmentBinding = GameFragmentBinding.bind(inflater.inflate(R.layout.game_fragment, container, false))
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        Log.i("Game Fragment", " here ______")
        val args = GameFragmentArgs.fromBundle(arguments!!)
        // set on swipe left and right



       Log.i("Game Fragment category:", "${args.categoryID}")
        // TODO: Use the ViewModel
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
