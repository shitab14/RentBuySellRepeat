package app.smir.rentbuysellrepeat.presentation.feature.product

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.viewpager2.widget.ViewPager2
import app.smir.rentbuysellrepeat.databinding.ActivityCreateProductBinding
import app.smir.rentbuysellrepeat.presentation.base.BaseActivity
import app.smir.rentbuysellrepeat.presentation.feature.product.adapter.CreateProductPagerAdapter
import app.smir.rentbuysellrepeat.presentation.feature.product.create.TitleFragment
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper

class CreateProductActivity : BaseActivity<ActivityCreateProductBinding>(
    ActivityCreateProductBinding::inflate
) {

    private val viewModel: ProductViewModel by viewModel()
    private lateinit var pagerAdapter: CreateProductPagerAdapter
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            viewModel.setProductImageUri(it)
        }
    }

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
                    // binding.viewPager.currentItem += 1 // TODO: SHITAB will revert after adding other fragments [WIP]
                } else {
                    viewModel.createProduct()
                }
            }
        }
    }

    private fun validateCurrentStep(): Boolean {
        return when (binding.viewPager.currentItem) {
            0 -> {
                val fragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}") as? TitleFragment
                viewModel.saveTitle(fragment?.getProductTitle() ?: "")
                viewModel.validateTitle()

            }
            1 -> viewModel.validateCategories()
            2 -> viewModel.validateDescription()
            3 -> viewModel.validateImage()
            4 -> viewModel.validatePrice()
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

    fun openImagePicker() {
        imagePickerLauncher.launch("image/*")
    }
}