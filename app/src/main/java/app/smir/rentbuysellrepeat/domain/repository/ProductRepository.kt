package app.smir.rentbuysellrepeat.domain.repository

import app.smir.rentbuysellrepeat.data.database.entity.ProductEntity
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import app.smir.rentbuysellrepeat.data.model.product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

interface ProductRepository {
 suspend fun getProducts(): ResultWrapper<Response<List<ProductResponse>>>
 suspend fun getProductDetails(id: Int): ResultWrapper<Response<ProductResponse>>
 suspend fun createProduct(
  seller: RequestBody,
  title: RequestBody,
  description: RequestBody,
  categories: List<String>,
  productImage: MultipartBody.Part?,
  purchasePrice: RequestBody,
  rentPrice: RequestBody,
  rentOption: RequestBody
 ): ResultWrapper<Response<ProductResponse>>
 suspend fun updateProduct(
  id: Int,
  request: UpdateProductRequest
 ): ResultWrapper<Response<ProductResponse>>
 suspend fun saveProductLocally(product: ProductEntity)
 suspend fun getProductLocally(): List<ProductEntity>
 suspend fun deleteProductLocally()
 suspend fun deleteProduct(id: String): ResultWrapper<Response<Void>>
 suspend fun getCategories(): ResultWrapper<Response<List<CategoryResponse>>>
}