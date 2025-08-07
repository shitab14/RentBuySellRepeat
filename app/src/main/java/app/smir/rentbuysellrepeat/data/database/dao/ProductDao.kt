package app.smir.rentbuysellrepeat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import app.smir.rentbuysellrepeat.data.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductEntity>)

//    @Query("SELECT * FROM products WHERE id = :productId")
//    suspend fun getProductById(productId: Int): ProductEntity?

//    @Query("SELECT * FROM products")
//    fun getAllProducts(): List<ProductEntity>

//    @Query("DELETE FROM products WHERE id = :productId")
//    suspend fun deleteProductById(productId: Int)

//    @Query("DELETE FROM products")
//    suspend fun deleteAllProducts()
}