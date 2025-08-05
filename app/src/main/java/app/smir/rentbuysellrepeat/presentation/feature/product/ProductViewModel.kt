package app.smir.rentbuysellrepeat.presentation.feature.product

/**
Created by Shitab Mir on 4/8/25.
shitabmir@gmail.com
 **/
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.smir.rentbuysellrepeat.data.model.product.*
import app.smir.rentbuysellrepeat.domain.usecase.auth.LogoutUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.*
import app.smir.rentbuysellrepeat.presentation.feature.product.create.CreateProductJourneySingleton
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _products = MutableLiveData<ResultWrapper<Response<List<ProductResponse>>>>()
    val products: LiveData<ResultWrapper<Response<List<ProductResponse>>>> = _products

    private val _productDetails = MutableLiveData<ResultWrapper<Response<ProductResponse>>>()
    val productDetails: LiveData<ResultWrapper<Response<ProductResponse>>> = _productDetails

    private val _createProductResult = MutableLiveData<ResultWrapper<Response<ProductResponse>>>()
    val createProductResult: LiveData<ResultWrapper<Response<ProductResponse>>> = _createProductResult

    private val _deleteResult = MutableLiveData<ResultWrapper<Response<Void>>>()
    val deleteResult: LiveData<ResultWrapper<Response<Void>>> = _deleteResult

    private val _categories = MutableLiveData<ResultWrapper<Response<List<CategoryResponse>>>>()
    val categories: LiveData<ResultWrapper<Response<List<CategoryResponse>>>> = _categories

    private val _currentStep = MutableLiveData(1)
    val currentStep: LiveData<Int> = _currentStep

    private var productImageUri: Uri? = null

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = ResultWrapper.Loading
            _products.value = getProductsUseCase()
        }
    }

    fun loadProductDetails(id: Int) {
        viewModelScope.launch {
            _productDetails.value = ResultWrapper.Loading
            _productDetails.value = getProductDetailsUseCase(id)
        }
    }

    fun createProduct() {
        viewModelScope.launch {
            _createProductResult.value = ResultWrapper.Loading
            // TODO: SHITAB will add product creation logic here
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            _deleteResult.value = ResultWrapper.Loading
            _deleteResult.value = deleteProductUseCase(id)
        }
    }

    fun showDeleteConfirmation(id: Int) {
        // TODO: SHITAB will add delete confirmation dialog logic here
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = ResultWrapper.Loading
            _categories.value = getCategoriesUseCase()
        }
    }

    fun setCurrentStep(step: Int) {
        _currentStep.value = step
    }

    fun setProductImageUri(uri: Uri) {
        productImageUri = uri
    }

    fun validateTitle(): Boolean {
        // TODO: SHITAB will add title validation logic here
        return true
    }

    fun validateCategories(): Boolean {
        // TODO: SHITAB will add categories validation logic here
        return true
    }

    fun validateDescription(): Boolean {
        // TODO: SHITAB will add description validation logic here
        return true
    }

    fun validateImage(): Boolean {
        // TODO: SHITAB will add image validation logic here
        return productImageUri != null
    }

    fun validatePrice(): Boolean {
        // TODO: SHITAB will add price validation logic here
        return true
    }

    suspend fun logout() {
        logoutUseCase.invoke()
    }

    fun saveTitle(title: String) {
        CreateProductJourneySingleton.updateTitle(title = title)
    }

    fun getTitle(): String {
        return CreateProductJourneySingleton.getTitle()
    }

    fun clearAllInput() {
        return CreateProductJourneySingleton.clearAll()
    }


}