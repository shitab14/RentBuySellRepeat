package app.smir.rentbuysellrepeat.domain.usecase.product

import app.smir.rentbuysellrepeat.data.database.entity.ProductEntity
import app.smir.rentbuysellrepeat.domain.repository.ProductRepository
import javax.inject.Inject

/**
Created by Shitab Mir on 7/8/25.
shitabmir@gmail.com
 **/
class GetCacheProductLocallyUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<ProductEntity> {
        return repository.getProductLocally()
    }
}