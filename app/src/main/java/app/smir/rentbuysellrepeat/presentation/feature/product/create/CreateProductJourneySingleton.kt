package app.smir.rentbuysellrepeat.presentation.feature.product.create

import android.net.Uri
import app.smir.rentbuysellrepeat.data.model.product.CategoryResponse
import app.smir.rentbuysellrepeat.util.helper.AppLogger
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

 // Product Categories
 private val _cCategories = MutableStateFlow<List<CategoryResponse>?>(null)
 val cCategories = _cCategories.asStateFlow()

 fun updateCategories(cCategories: List<CategoryResponse>) {
  _cCategories.value = cCategories
 }

 fun getCategories(): List<CategoryResponse> {
  return cCategories.value ?: emptyList()
 }

 fun clearCategories() {
  _cCategories.value = null
 }

 // Product Description
 private val _cDescription = MutableStateFlow<String?>(null)
 val cDescription = _cDescription.asStateFlow()

 fun updateDescription(description: String) {
  _cDescription.value = description
 }

 fun getDescription(): String {
  return cDescription.value ?: ""
 }

 fun clearDescription() {
  _cDescription.value = ""
 }

 // Product Images
 private val _imageUris = mutableListOf<Uri>()

 val imageUris: List<Uri>
  get() = _imageUris

 fun addImageUri(uri: Uri) {
  _imageUris.add(uri)
 }

 fun addImageUris(uris: List<Uri>) {
  _imageUris.addAll(uris)
 }

 fun clearImages() {
  _imageUris.clear()
 }

 // Product Price
 // Purchase Price
 private val _cPurchase = MutableStateFlow<String?>(null)
 val cPurchase = _cPurchase.asStateFlow()

 fun updatePurchasePrice(purchasePrice: String) {
  _cPurchase.value = purchasePrice
 }

 fun getPurchasePrice(): String {
  return cPurchase.value ?: ""
 }

 fun clearPurchasePrice() {
  _cPurchase.value = ""
 }

 // Rent Price
 private val _cRent = MutableStateFlow<String?>(null)
 val cRent = _cRent.asStateFlow()

 fun updateRentPrice(rentPrice: String) {
  _cRent.value = rentPrice
 }

 fun getRentPrice(): String {
  return cRent.value ?: ""
 }

 fun clearRentPrice() {
  _cRent.value = ""
 }

 // Rent Option
 private val _cRentOption = MutableStateFlow<String?>(null)
 val cRentOption = _cRentOption.asStateFlow()

 fun updateRentOption(rentOption: String) {
  _cRentOption.value = rentOption
 }

 fun getRentOption(): String {
  return cRentOption.value ?: ""
 }

 fun clearRentOption() {
  _cRentOption.value = ""
 }

 // ALL
 fun clearAll() {
  clearTitle()
  clearCategories()
  clearDescription()
  clearImages()
  clearPurchasePrice()
  clearRentPrice()
  clearRentOption()
 }


}