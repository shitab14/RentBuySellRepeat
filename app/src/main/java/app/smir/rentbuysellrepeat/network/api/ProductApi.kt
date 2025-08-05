package app.smir.rentbuysellrepeat.network.api

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import app.smir.rentbuysellrepeat.data.model.product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    @GET("api/products/")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("api/products/products.json")
    suspend fun getProductsMock(): Response<List<ProductResponse>>

    @GET("api/products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): Response<ProductResponse>

    @Multipart
    @POST("api/products/")
    suspend fun createProduct(
        @Part("seller") seller: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("categories") categories: RequestBody,
        @Part productImage: MultipartBody.Part,
        @Part("purchase_price") purchasePrice: RequestBody,
        @Part("rent_price") rentPrice: RequestBody,
        @Part("rent_option") rentOption: RequestBody
    ): Response<ProductResponse>

    @PUT("api/products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body request: UpdateProductRequest
    ): Response<ProductResponse>

    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Void>

    @GET("api/products/categories/")
    suspend fun getCategories(): Response<List<CategoryResponse>>

    @GET("api/products/categories.json")
    suspend fun getCategoriesMock(): Response<List<CategoryResponse>>
}