package com.example.krokodyl.category


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.krokodyl.databinding.CategoryItemBinding
import com.example.krokodyl.model.Category

class CategoryRecyclerViewAdapter (val clickListener: OnCategoryClickListener): RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {


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

     class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {



       fun bind(item: Category, clickListener: OnCategoryClickListener) {
           binding.category = item
           binding.clickListener = clickListener
           binding.executePendingBindings()

       }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }}


    }

 // class used to check if there any changes in date and redrew only changed items if they are on screen
    class CategoryDiffCallback :
        DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}


