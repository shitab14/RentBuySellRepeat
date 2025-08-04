package app.smir.rentbuysellrepeat.presentation.feature.product.adapter

/**
Created by Shitab Mir on 4/8/25.
shitabmir@gmail.com
 **/
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.smir.rentbuysellrepeat.R
import app.smir.rentbuysellrepeat.data.model.product.ProductResponse
import app.smir.rentbuysellrepeat.databinding.ItemProductBinding
import com.bumptech.glide.Glide

class ProductAdapter(
    private val onItemClick: (ProductResponse) -> Unit,
    private val onDeleteClick: (ProductResponse) -> Unit
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
                tvPrice.text = "$${product.purchase_price}"
                tvRentPrice.text = "${product.rent_price}/${product.rent_option}"

                Glide.with(root.context)
                    .load(product.product_image)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(ivProduct)

                btnDelete.setOnClickListener { onDeleteClick(product) }
                root.setOnClickListener { onItemClick(product) }
            }
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