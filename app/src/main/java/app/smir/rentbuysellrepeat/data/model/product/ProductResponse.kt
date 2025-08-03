package app.smir.rentbuysellrepeat.data.model.product

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
data class ProductResponse(
 val id: Int,
 val seller: Int,
 val title: String,
 val description: String,
 val categories: List<String>,
 val product_image: String,
 val purchase_price: String,
 val rent_price: String,
 val rent_option: String,
 val date_posted: String
)