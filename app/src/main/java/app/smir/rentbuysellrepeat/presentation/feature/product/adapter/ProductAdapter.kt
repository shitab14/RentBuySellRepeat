package app.smir.rentbuysellrepeat.presentation.feature.product.adapter

/**
Created by Shitab Mir on 4/8/25.
shitabmir@gmail.com
 **/
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.smir.rentbuysellrepeat.R
import app.smir.rentbuysellrepeat.data.model.product.ProductResponse
import app.smir.rentbuysellrepeat.databinding.ItemProductBinding
import app.smir.rentbuysellrepeat.util.helper.TimeDateUtil
import com.bumptech.glide.Glide

class ProductAdapter(
    private val onItemClick: (ProductResponse) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : ListAdapter<ProductResponse, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(product: ProductResponse) {
            binding.apply {
                tvTitle.text = product.title

                var categoriesText = "Categories: "
                if(product.categories.isNotEmpty()) {
                    for (category in product.categories) {
                        categoriesText = "$categoriesText$category, "
                    }
                    categoriesText = categoriesText.dropLast(2)
                } else {
                    categoriesText = ""
                }
                tvCategories.text = categoriesText

                tvRentPrice.text = "Price: ${product.purchase_price} | Rent: ${product.rent_price}/${product.rent_option}"

                tvDescription.maxLines = 2
                tvDescription.text = "Description: ${product.description}"

                checkDescriptionCharacterLimit(tvDescription, tvMoreDetails)

                tvMoreDetails.setOnClickListener {
                    tvDescription.maxLines = Int.MAX_VALUE
                    tvMoreDetails.visibility = View.GONE
                }

                tvDescription.setOnClickListener {
                    tvDescription.maxLines = 2
                    checkDescriptionCharacterLimit(tvDescription, tvMoreDetails)
                }

                tvDatePosted.text = "Date Posted: ${TimeDateUtil.formatIsoDateWithSuffix(product.date_posted)}"

                Glide.with(root.context)
                    .load(product.product_image)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(ivProduct)

                btnDelete.setOnClickListener { onDeleteClick(product.id.toString()) }
                root.setOnClickListener { onItemClick(product) }
            }
        }

        private fun checkDescriptionCharacterLimit(
            tvDescription: TextView,
            tvMoreDetails: TextView
        ) {
            if (tvDescription.text.length > 50) {
                tvMoreDetails.visibility = View.VISIBLE
            } else tvMoreDetails.visibility = View.GONE
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem == newItem
        }
    }
}