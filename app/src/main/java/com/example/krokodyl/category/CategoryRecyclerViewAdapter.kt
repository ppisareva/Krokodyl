package com.example.krokodyl.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.krokodyl.databinding.CategoryItemBinding
import com.example.krokodyl.model.Category


class CategoryRecyclerViewAdapter (private val clickListener: OnCategoryClickListener): RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {


    var data = listOf<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)

    }


    override fun getItemCount(): Int = data.size

     class ViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {



       fun bind(item: Category, clickListener: OnCategoryClickListener) {

           binding.category = item
           binding.executePendingBindings()
           binding.gameFragmentLayout

           binding.clickListener = clickListener


       }
       // encapsulating inflating layout
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }}


    }


}


