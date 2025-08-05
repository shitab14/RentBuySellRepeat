package app.smir.rentbuysellrepeat.presentation.feature.product.create

import app.smir.rentbuysellrepeat.data.model.product.CategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/
object CreateProductJourneySingleton {

 // Product Title
 private val _cTitle = MutableStateFlow<String?>(null)
 val cTitle = _cTitle.asStateFlow()

 // Product Categories
 private val _cCategories = MutableStateFlow<List<CategoryResponse>?>(null)
 val cCategories = _cCategories.asStateFlow()

 fun updateTitle(title: String) {
  _cTitle.value = title
 }

 fun getTitle(): String {
  return cTitle.value ?: ""
 }

 fun clearTitle() {
  _cTitle.value = null
 }

 fun updateCategories(cCategories: List<CategoryResponse>) {
  _cCategories.value = cCategories
 }

 fun getCategories(): List<CategoryResponse> {
  return cCategories.value ?: emptyList()
 }

 fun clearCategories() {
  _cCategories.value = null
 }

 // ALL
 fun clearAll() {
  clearTitle()
  clearCategories()
 }


}