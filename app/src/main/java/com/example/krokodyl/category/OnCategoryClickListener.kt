package com.example.krokodyl.category

import com.example.krokodyl.model.Category

class OnCategoryClickListener (val clickListener: (categoryId: Int) -> Unit) {
   fun onCategoryClick(category: Category) = clickListener(category.categoryId)
}
