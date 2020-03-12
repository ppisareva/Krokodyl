package com.example.krokodyl.category

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.krokodyl.R
import com.example.krokodyl.databinding.CategoryFragmentBinding
import kotlinx.android.synthetic.main.activity_main.*

// todo first open app slow, changing category order
class CategoryFragment : Fragment() {

    lateinit var viewModel: CategoryViewModel
    lateinit var viewModelFactory: CategoryViewModelFactory
    lateinit var binding : CategoryFragmentBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.category_fragment,
            container,
            false
        )
        (activity as AppCompatActivity).toolbar?.title = getString(R.string.choose_category)

        var  application = checkNotNull(this.activity).application


        viewModelFactory = CategoryViewModelFactory( application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)


        val adapter = CategoryRecyclerViewAdapter(OnCategoryClickListener { category ->
            Log.i("Click made" , category.idCategory)
            viewModel.eventStartGame(category)
        })
        binding.categoryRv.adapter = adapter
        binding.categoryViewModel = viewModel
        val layoutManager = GridLayoutManager(activity, 2)
        binding.categoryRv.layoutManager = layoutManager

        viewModel.categoriesList.observe(this, Observer { categoryList ->
           if(adapter.data.isEmpty()) {
               Log.e("adapter is empty", "load data")
               adapter.data = categoryList
           }
        })

        viewModel.currentCategory.observe(this, Observer { category ->
            // if category !=null than do what after let
           Log.i("Navigation", " to fragment game with category $category")
            category?.let{
                Navigation.findNavController(binding.root)
                    .navigate(
                       CategoryFragmentDirections.actionCategoryFragmentToGameFragment(it))
                viewModel.navigationFinished()
            }

        })




        return binding.root
    }



}
