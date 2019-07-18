package com.example.krokodyl.category

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
import com.example.krokodyl.databinding.CategoryFragmentBinding


class CategoryFragment : Fragment() {

    lateinit var viewModel: CategoryViewModel
    lateinit var binding : CategoryFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.category_fragment,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        val adapter = CategoryRecyclerViewAdapter(OnCategoryClickListener { categoryId ->
            Log.i("Click made" , categoryId.toString())
            viewModel.eventStartGame(categoryId)
        })
        binding.categoryRv.adapter = adapter
        viewModel.categoriesList.observe(this, Observer { categoryList ->
            adapter.data = categoryList
        })

        viewModel.currentCategory.observe(this, Observer { categoryId ->
            // if category !=null than do what after let
           Log.i("category", "$categoryId")
            categoryId?.let{
                Navigation.findNavController(binding.root)
                    .navigate(
                       CategoryFragmentDirections.actionCategoryFragmentToGameFragment(it))
                viewModel.navigationFinished()
            }

        })
        return binding.root
    }



}
