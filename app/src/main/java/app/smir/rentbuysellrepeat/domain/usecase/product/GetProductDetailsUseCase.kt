package app.smir.rentbuysellrepeat.domain.usecase.product

import app.smir.rentbuysellrepeat.data.model.product.ProductResponse
import app.smir.rentbuysellrepeat.domain.repository.ProductRepository
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
class GetProductDetailsUseCase @Inject constructor(
 private val repository: ProductRepository
) {
 suspend operator fun invoke(id: Int): ResultWrapper<Response<ProductResponse>> {
  return repository.getProductDetails(id)
 }
}