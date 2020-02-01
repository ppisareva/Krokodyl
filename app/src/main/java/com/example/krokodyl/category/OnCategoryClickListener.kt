package com.example.krokodyl.category

import com.example.krokodyl.model.Category
// TODO
class OnCategoryClickListener (val clickListener: (category: Category) -> Unit) {

   fun onCategoryClick(category: Category) = clickListener(category)
}
