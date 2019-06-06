package com.example.krokodyl

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.krokodyl.dummy.DummyContent
import com.example.krokodyl.dummy.DummyContent.DummyItem

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CategoryFragment.OnListFragmentInteractionListener] interface.
 */
class CategoryFragment : Fragment(), OnListFragmentInteractionListener {

    lateinit var viewModel: CategoryViewModel

    override fun onListFragmentInteraction(item: DummyItem?){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  GridLayoutManager(context, 2)
                adapter = MyCategoryRecyclerViewAdapter(DummyContent.ITEMS, viewModel)
            }
        }

        // Create the observer which updates the UI.
        val taskObserver = Observer<Int> { categoryId ->
            // Update the UI, start navigation to another fragment.
            Log.i("Categoryftagment", "start navigation with id $categoryId")
            if(categoryId>0)     Navigation.findNavController(view!!).navigate(R.id.action_categoryFragment_to_gameFragment)
    }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.categoryID.observe(this, taskObserver)
        return view
    }

}
