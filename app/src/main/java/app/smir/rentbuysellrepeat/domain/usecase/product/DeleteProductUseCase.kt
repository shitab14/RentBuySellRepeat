package app.smir.rentbuysellrepeat.domain.usecase.product

import app.smir.rentbuysellrepeat.domain.repository.ProductRepository
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): ResultWrapper<Response<Void>> {
        return repository.deleteProduct(id)
    }
}