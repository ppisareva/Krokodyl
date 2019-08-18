package com.example.krokodyl.category

import com.example.krokodyl.model.Category

class OnCategoryClickListener (val clickListener: (categoryId: String) -> Unit) {
   fun onCategoryClick(category: Category) = clickListener(category.categoryId)
}
