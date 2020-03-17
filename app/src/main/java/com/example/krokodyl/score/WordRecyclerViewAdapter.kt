package com.example.krokodyl.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.krokodyl.databinding.WordsItemBinding
import com.example.krokodyl.model.Word


class WordRecyclerViewAdapter (): RecyclerView.Adapter<WordRecyclerViewAdapter.ViewHolder>() {


    var data = listOf<Word>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }


    override fun getItemCount(): Int = data.size

    class ViewHolder(private val binding: WordsItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Word) {
            binding.word = item
           }

        // encapsulating inflating layout
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }


    }}