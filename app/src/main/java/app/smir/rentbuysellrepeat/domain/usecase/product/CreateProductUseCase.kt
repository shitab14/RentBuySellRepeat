package app.smir.rentbuysellrepeat.domain.usecase.product

import app.smir.rentbuysellrepeat.data.model.product.ProductResponse
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
class CreateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        seller: RequestBody,
        title: RequestBody,
        description: RequestBody,
        categories: List<String>,
        productImage: MultipartBody.Part?,
        purchasePrice: RequestBody,
        rentPrice: RequestBody,
        rentOption: RequestBody
    ): ResultWrapper<Response<ProductResponse>> {
        return repository.createProduct(
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
}