package com.example.krokodyl.category

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.krokodyl.R
import com.example.krokodyl.model.Category

@BindingAdapter("categoryTitle")
    fun TextView.setCategoryTitle(item: Category?) {
        item?.let {
            text = it.nameCategory
        }
    }



    @BindingAdapter("categoryImage")
    fun bindImage(imgView: ImageView, item: Category?) {
        item?.let {
           // val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(item.imageCategory)
                .apply(
                    RequestOptions().circleCrop()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
                .into(imgView)
        }
    }




