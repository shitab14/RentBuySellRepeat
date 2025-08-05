package app.smir.rentbuysellrepeat.presentation.feature.product.create

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

 fun updateTitle(title: String) {
  _cTitle.value = title
 }

 fun getTitle(): String {
  return cTitle.value ?: ""
 }

 fun clearTitle() {
  _cTitle.value = null
 }


 // ALL
 fun clearAll() {
  clearTitle()
 }


}