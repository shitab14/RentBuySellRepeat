package app.smir.rentbuysellrepeat.data.source.remote

import app.smir.rentbuysellrepeat.BuildConfig
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import app.smir.rentbuysellrepeat.util.helper.network.safeApiCall
import app.smir.rentbuysellrepeat.data.model.product.*
import app.smir.rentbuysellrepeat.network.api.ProductApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

class ProductRemoteDataSource @Inject constructor(
    private val productApi: ProductApi
) {
    suspend fun getProducts(): ResultWrapper<Response<List<ProductResponse>>> {
        return if (BuildConfig.FLAVOR.equals("dev")) {
            safeApiCall { productApi.getProductsMock() }
        } else{
            safeApiCall { productApi.getProducts() }
        }
    }

    suspend fun getProductDetails(id: Int): ResultWrapper<Response<ProductResponse>> {
        return safeApiCall { productApi.getProductDetails(id) }
    }

    suspend fun createProduct(
        seller: RequestBody,
        title: RequestBody,
        description: RequestBody,
        categories: List<String>,
        productImage: MultipartBody.Part?,
        purchasePrice: RequestBody,
        rentPrice: RequestBody,
        rentOption: RequestBody
    ): ResultWrapper<Response<ProductResponse>> {
        return safeApiCall {
            productApi.createProduct(
                seller,
                title,
                description,
                categories,
                productImage ?: MultipartBody.Part.createFormData("productImage", ""),
                purchasePrice,
                rentPrice,
                rentOption
            )
        }
    }

    suspend fun updateProduct(
        id: Int,
        request: UpdateProductRequest
    ): ResultWrapper<Response<ProductResponse>> {
        return safeApiCall { productApi.updateProduct(id, request) }
    }

    suspend fun deleteProduct(id: String): ResultWrapper<Response<Void>> {
        return if (BuildConfig.FLAVOR.equals("dev")) {
            safeApiCall { productApi.deleteProductMock(id) }
        } else{
            safeApiCall { productApi.deleteProduct(id) }
        }
    }

    suspend fun getCategories(): ResultWrapper<Response<List<CategoryResponse>>> {
        return safeApiCall { productApi.getCategories() }
    }

    suspend fun getCategoriesMock(): ResultWrapper<Response<List<CategoryResponse>>> {
        return safeApiCall { productApi.getCategoriesMock() }
    }
}