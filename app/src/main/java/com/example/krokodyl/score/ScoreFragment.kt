package com.example.krokodyl.score

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krokodyl.R
import com.example.krokodyl.databinding.ScoreFragmentBinding
import com.example.krokodyl.model.Team
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.score_fragment.*


class ScoreFragment : Fragment() {


    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory : ScoreViewModelFactory
    lateinit var binding : ScoreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding  = DataBindingUtil.inflate(
            inflater, R.layout.score_fragment, container, false)
            return binding.root    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val score = ScoreFragmentArgs.fromBundle(arguments!!).score
        val category = ScoreFragmentArgs.fromBundle(arguments!!).category
        val words = ScoreFragmentArgs.fromBundle(arguments!!).listOfWords
        val teams = words.listOfTeam
        Log.e("list of words", " size ${words.wordsList.size}")
        Log.i("score", score.toString())
        viewModelFactory = ScoreViewModelFactory(score)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)

        // adapter
        val adapter = WordRecyclerViewAdapter()
        binding.listOfWords.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.listOfWords.layoutManager = layoutManager
        adapter.data = words.wordsList
       checkNumberOfTeams(teams)
        // bind xml with view to view model
        binding.scoreViewModel = viewModel
        (activity as AppCompatActivity).toolbar?.title = getString(R.string.back)
        (activity as AppCompatActivity).toolbar?.menu?.findItem(R.id.action_categoryFragment_to_mySettingsFragment)!!.setVisible(false)



        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.setLifecycleOwner(this)

        viewModel.eventNewGame.observe(this, Observer { starNewGame ->
            if(starNewGame){
                words.wordsList = mutableListOf()
                Navigation.findNavController(view!!)
                    .navigate(
                        ScoreFragmentDirections.actionScoreFragmentToGameFragment(category, words))
                viewModel.onEventStartNewGameEnded()
            }
        })

        viewModel.eventToCategory.observe(this, Observer { toCategory->
            if(toCategory) {
                Navigation.findNavController(view!!).navigate(R.id.action_scoreFragment_to_categoryFragment)
                viewModel.onReturnToCategoryEventEnded()
            }
        })

    }

    private fun checkNumberOfTeams(teams:MutableList<Team>) {
        if(teams.size >0){
            team_score_ll.visibility = View.VISIBLE
            viewModel.teamOne.value = "Команда 1: ${teams[0].teamScore}"
            viewModel.teamTwo.value = "Команда 2: ${teams[1].teamScore}"
            if(teams.size==2){
                team_three.visibility = View.GONE
            } else
            {
                viewModel.teamThree.value = "Команда 3: ${teams[2].teamScore}"
            }
        }

    }


}
