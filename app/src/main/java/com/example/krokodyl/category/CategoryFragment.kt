package com.example.krokodyl.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.krokodyl.R
import com.example.krokodyl.databinding.FragmentCategoryBinding
import com.example.krokodyl.dummy.DummyContent


class CategoryFragment : Fragment(){

    lateinit var viewModel: CategoryViewModel
    lateinit var taskObserver : Observer<Boolean>
    lateinit var binding : FragmentCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding : FragmentCategoryBinding = DataBindingUtil.inflate(
           inflater, R.layout.fragment_category, container, false)
        val view = binding.root

        // Set the adapter todo binding
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  GridLayoutManager(context, 2)
                adapter = MyCategoryRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }

        // Create the observer which updates the UI.
        taskObserver = Observer<Boolean> { isCategoryChosen ->
            // Update the UI, start navigation to another fragment.
            if(isCategoryChosen){
                navigateToGameFragment()
                viewModel.navigationFinished()
            }
    }
        viewModel.onCategoryChooseEvent.observe(this, taskObserver)

        return view
    }
// delete
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Navigation.findNavController(view)
            .navigate(
                CategoryFragmentDirections.actionCategoryFragmentToGameFragment(
                    viewModel.categoryID.value!!
                )
            )
    }

    fun navigateToGameFragment(){
        Navigation.findNavController(view!!)
            .navigate(
                CategoryFragmentDirections.actionCategoryFragmentToGameFragment(
                    viewModel.categoryID.value!!
                )
            )
    }
}
