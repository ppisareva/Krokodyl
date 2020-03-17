package com.example.krokodyl

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.krokodyl.model.Category
import com.example.krokodyl.model.Word

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

@BindingAdapter("word")
fun TextView.setWord(item: Word?) {
    item?.let {
        text = it.word
        setTextColor( if(it.gussedStatus) resources.getColor(R.color.primaryColor)
        else resources.getColor(R.color.secondaryDarkColor))
    }
}

@BindingAdapter("guess")
fun ImageView.setGuessed(item: Word?) {
    item?.let {
        setImageResource( if(it.gussedStatus) R.drawable.baseline_done_black else R.drawable.baseline_clear_black)
    }
}




