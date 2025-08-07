package app.smir.rentbuysellrepeat.data.repository

import app.smir.rentbuysellrepeat.BuildConfig
import app.smir.rentbuysellrepeat.data.database.entity.ProductEntity
import app.smir.rentbuysellrepeat.data.model.product.*
import app.smir.rentbuysellrepeat.data.source.local.ProductLocalDataSource
import app.smir.rentbuysellrepeat.data.source.remote.ProductRemoteDataSource
import app.smir.rentbuysellrepeat.domain.repository.ProductRepository
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun getProducts(): ResultWrapper<Response<List<ProductResponse>>> {
        return remoteDataSource.getProducts()
    }

    override suspend fun getProductDetails(id: Int): ResultWrapper<Response<ProductResponse>> {
        return remoteDataSource.getProductDetails(id)
    }

    override suspend fun createProduct(
        seller: RequestBody,
        title: RequestBody,
        description: RequestBody,
        categories: List<String>,
        productImage: MultipartBody.Part?,
        purchasePrice: RequestBody,
        rentPrice: RequestBody,
        rentOption: RequestBody
    ): ResultWrapper<Response<ProductResponse>> {
        return remoteDataSource.createProduct(
                seller,
                title,
                description,
                categories,
                productImage,
                purchasePrice,
                rentPrice,
                rentOption
            )
        }


    override suspend fun updateProduct(
        id: Int,
        request: UpdateProductRequest
    ): ResultWrapper<Response<ProductResponse>> {
        return remoteDataSource.updateProduct(id, request)
    }

    override suspend fun saveProductLocally(product: ProductEntity) {
        localDataSource.cacheOnGoingProduct(product)
    }

    override suspend fun getProductLocally(): List<ProductEntity> {
        return localDataSource.getCachedProducts()
    }

    override suspend fun deleteProductLocally() {
        localDataSource.removeOnGoingProduct()
    }

    override suspend fun deleteProduct(id: String): ResultWrapper<Response<Void>> {
        return remoteDataSource.deleteProduct(id)
    }

    override suspend fun getCategories(): ResultWrapper<Response<List<CategoryResponse>>> {
        return if(BuildConfig.FLAVOR.equals("dev")) {
            remoteDataSource.getCategoriesMock()
        } else {
            remoteDataSource.getCategories()
        }
    }
}