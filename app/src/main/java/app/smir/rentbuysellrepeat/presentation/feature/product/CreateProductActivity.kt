package app.smir.rentbuysellrepeat.presentation.feature.product

import android.content.Intent
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.viewpager2.widget.ViewPager2
import app.smir.rentbuysellrepeat.databinding.ActivityCreateProductBinding
import app.smir.rentbuysellrepeat.presentation.base.BaseActivity
import app.smir.rentbuysellrepeat.presentation.feature.product.adapter.CreateProductPagerAdapter
import app.smir.rentbuysellrepeat.presentation.feature.product.create.CategoriesFragment
import app.smir.rentbuysellrepeat.presentation.feature.product.create.DescriptionFragment
import app.smir.rentbuysellrepeat.presentation.feature.product.create.PriceFragment
import app.smir.rentbuysellrepeat.presentation.feature.product.create.TitleFragment
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.AppLogger
import app.smir.rentbuysellrepeat.util.helper.MultipartHelper
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CreateProductActivity : BaseActivity<ActivityCreateProductBinding>(
    ActivityCreateProductBinding::inflate
) {

    private val viewModel: ProductViewModel by viewModel()
    private lateinit var pagerAdapter: CreateProductPagerAdapter

    override fun setupViews() {
        setupViewPager()
        setupClickListeners()
    }

    override fun setupObservers() {
        viewModel.createProductResult.observe(this) { result ->
            when (result) {
                is ResultWrapper.Loading -> showLoading()
                is ResultWrapper.Success -> {
                    hideLoading()
                    navigateToMyProducts()
                }
                is ResultWrapper.Error -> {
                    hideLoading()
                    binding.root.showSnackBar(result.message ?: "Failed to create product")
                }

                ResultWrapper.Empty -> {
                    hideLoading()
                    binding.root.showSnackBar("Failed to create product")
                }
            }
        }

        viewModel.currentStep.observe(this) { step ->
            binding.progressBar.progress = step
            binding.viewPager.currentItem = step - 1
            updateButtonVisibility(step)
        }
    }

    private fun setupViewPager() {
        pagerAdapter = CreateProductPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentStep(position + 1)
            }
        })
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            if (binding.viewPager.currentItem > 0) {
                binding.viewPager.currentItem -= 1
            } else {
                viewModel.clearAllInput()
                finish()
            }
        }

        binding.btnNext.setOnClickListener {
            if (validateCurrentStep()) {
                if (binding.viewPager.currentItem < pagerAdapter.itemCount - 1) {
                    // 0-4
                    binding.viewPager.currentItem += 1
                }
                else {
                    // 5

                    makeMultipartAndCall()
                    //this@CreateProductActivity.finish()
                }
            } else {
                binding.root.showSnackBar("Please fill in the required fields")
            }
        }
    }

    private fun makeMultipartAndCall() {
        val categoryValues: List<String> = viewModel.getSavedCategories().map {
            it.value
        }

        lifecycleScope.launch {
            try {
                val sellerPart = MultipartHelper.createTextPart("seller", "0")
                val titlePart = MultipartHelper.createTextPart("title", viewModel.getTitle())
                val descriptionPart = MultipartHelper.createTextPart("description", viewModel.getDescription())

                val categories = categoryValues

                val purchasePricePart = MultipartHelper.createTextPart("purchase_price", viewModel.getPurchasePrice())
                val rentPricePart = MultipartHelper.createTextPart("rent_price", viewModel.getRentPrice())
                val rentOptionPart = MultipartHelper.createTextPart("rent_option", viewModel.getRentOption())

                var imagePart:MultipartBody.Part? = null
                if(viewModel.getImages().isNotEmpty()) {
                     imagePart = MultipartHelper.createFilePart(
                        this@CreateProductActivity,
                        "product_image",
                        viewModel.getImages().first()
                    )
                }
                viewModel.createProduct(
                    sellerPart,
                    titlePart,
                    descriptionPart,
                    categories,
                    purchasePricePart,
                    rentPricePart,
                    rentOptionPart,
                    imagePart
                )


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



    }

    private fun validateCurrentStep(): Boolean {
        return when (binding.viewPager.currentItem) {
            0 -> {
                val fragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}") as? TitleFragment
                viewModel.saveTitle(fragment?.getProductTitle() ?: "")
                if(fragment?.validate() == true) {
                    true
                } else {
                    binding.root.showSnackBar("Please enter a title")
                    false
                }
                //viewModel.validateTitle()

            }
            1 -> {
                val fragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}") as? CategoriesFragment
                fragment?.getSelectedChipValues()?.let { viewModel.saveCategories(it) }
                if(fragment?.validate()==true) {
                    true
                } else {
                    binding.root.showSnackBar("Please select at least one category")
                    false
                }
//                    viewModel.validateCategories()
            }
            2 -> {
                val fragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}") as? DescriptionFragment

                if(fragment?.validate()==true) {
                    true
                } else {
                    binding.root.showSnackBar("Please enter a description")
                    false
                }
//                viewModel.validateDescription()
            }
            3 -> {
//                val fragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}") as? ImageFragment

//                if(fragment?.validate()==true) {
//                    true
//                } else {
//                    binding.root.showSnackBar("Please enter a description")
//                    false
//                }
//                viewModel.validateImage()

                return true
            }
            4 -> {
                AppLogger.e("This is number 4")
                val fragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}") as? PriceFragment
                if(fragment?.validate() == true) {
                    true
                } else {
                    binding.root.showSnackBar("Please enter a Purchase Price or Rent Price")
                    false
                }
//                viewModel.validatePrice()
            }
            else -> true
        }
    }

    private fun updateButtonVisibility(step: Int) {
        binding.btnNext.text = if (step == 6) "SUBMIT" else "NEXT"
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnNext.isEnabled = false
        binding.btnBack.isEnabled = false
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnNext.isEnabled = true
        binding.btnBack.isEnabled = true
    }

    private fun navigateToMyProducts() {
        startActivity(Intent(this, MyProductsActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        viewModel.clearAllInput()
        super.onDestroy()
    }
}