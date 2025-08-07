package app.smir.rentbuysellrepeat.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val seller: Int,
    val title: String,
    val description: String,
//    val categories: List<String>?,
    val productImage: String,
    val purchasePrice: String,
    val rentPrice: String,
    val rentOption: String,
    val datePosted: String
)