package com.example.krokodyl.game

import android.content.pm.ActivityInfo
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
import com.example.krokodyl.model.KrokodylDatabase


class GameFragment : Fragment()  {

    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var  binding : GameFragmentBinding
    private var categoryId :String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // binding data
        binding  = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        // get category selected from category fragment
        categoryId = GameFragmentArgs.fromBundle(arguments!!).categoryID
        Log.i("category Id ---", categoryId)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var  application = checkNotNull(this.activity).application
        val database = KrokodylDatabase.getInstance(application).categoryDatabaseDao

        viewModelFactory = GameViewModelFactory(categoryId,database, application )
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
