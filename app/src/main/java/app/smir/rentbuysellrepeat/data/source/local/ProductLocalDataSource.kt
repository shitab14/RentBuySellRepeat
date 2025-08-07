package app.smir.rentbuysellrepeat.data.source.local

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import app.smir.rentbuysellrepeat.data.database.entity.ProductEntity
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
// private val productDao: ProductDao
) {

 suspend fun cacheOnGoingProduct(products: ProductEntity) {
//  productDao.insertProduct(products)
 }

 suspend fun removeOnGoingProduct() {
//  productDao.deleteAllProducts()
 }

 suspend fun getCachedProducts(): List<ProductEntity> {
//  return productDao.getAllProducts()
  return emptyList()
 }
}