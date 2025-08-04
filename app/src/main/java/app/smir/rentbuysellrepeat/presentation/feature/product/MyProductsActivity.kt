package app.smir.rentbuysellrepeat.presentation.feature.product

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import app.smir.rentbuysellrepeat.databinding.ActivityMyProductsBinding
import app.smir.rentbuysellrepeat.presentation.base.BaseActivity
import app.smir.rentbuysellrepeat.presentation.feature.auth.LoginActivity
import app.smir.rentbuysellrepeat.presentation.feature.product.adapter.ProductAdapter
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import app.smir.rentbuysellrepeat.R

class MyProductsActivity : BaseActivity<ActivityMyProductsBinding>(
    ActivityMyProductsBinding::inflate
) {

    private val viewModel: ProductViewModel by viewModel()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var toggle: ActionBarDrawerToggle

    override fun setupViews() {
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        setupDrawer()
    }

    override fun setupObservers() {
        viewModel.products.observe(this) { result ->
            when (result) {
                is ResultWrapper.Loading -> showLoading()
                is ResultWrapper.Success -> {
                    hideLoading()
//                    productAdapter.submitList(result.data) // TODO: Shitab will ...
                }
                is ResultWrapper.Error -> {
                    hideLoading()
                    binding.root.showSnackBar(result.message ?: "Failed to load products")
                }

                ResultWrapper.Empty ->  {
                    hideLoading()
                    binding.root.showSnackBar("Failed to load products")
                }
            }
        }

        viewModel.deleteResult.observe(this) { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    viewModel.loadProducts()
                }
                is ResultWrapper.Error -> {
                    binding.root.showSnackBar(result.message ?: "Failed to delete product")
                }
                else -> {}
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onItemClick = { product ->
                // TODO: SHITAB will add navigation to edit product
            },
            onDeleteClick = { product ->
                viewModel.showDeleteConfirmation(product.id)
            }
        )

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(this@MyProductsActivity)
            adapter = productAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAddProduct.setOnClickListener {
//            startActivity(Intent(this, CreateProductActivity::class.java)) // TODO: SHITAB will ...
        }
    }

    private fun setupDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar, R.string.open_drawer, R.string.close_drawer
        )
        toggle.drawerArrowDrawable.color = getColor(R.color.purple_500)

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_my_products -> {
                    // Already on this page
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_all_products -> {
                    // TODO: SHITAB will add navigation to all products
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_logout -> {
                    viewModel.logout()
                    navigateToLogin()
                    true
                }
                else -> false
            }
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}