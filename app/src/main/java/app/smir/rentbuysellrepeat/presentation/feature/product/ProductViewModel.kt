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
import app.smir.rentbuysellrepeat.data.database.entity.ProductEntity
import app.smir.rentbuysellrepeat.data.model.product.*
import app.smir.rentbuysellrepeat.domain.usecase.auth.LogoutUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.*
import app.smir.rentbuysellrepeat.presentation.feature.product.create.CreateProductJourneySingleton
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
//    private val cacheProductLocallyUseCase: CacheProductLocallyUseCase,
//    private val getCacheProductLocallyUseCase: GetCacheProductLocallyUseCase,
//    private val clearCacheProductUseCase: ClearCacheProductUseCase,
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

//    private var productImageUri: Uri? = null

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

    fun createProduct(
        sellerPart: MultipartBody.Part,
        titlePart: MultipartBody.Part,
        descriptionPart: MultipartBody.Part,
        categories: List<String>,
        purchasePricePart: MultipartBody.Part,
        rentPricePart: MultipartBody.Part,
        rentOptionPart: MultipartBody.Part,
        imagePart: MultipartBody.Part?,
    ) {
        viewModelScope.launch {

            _createProductResult.value = ResultWrapper.Loading

            _createProductResult.value = createProductUseCase.invoke(
                seller = sellerPart.body,
                title = titlePart.body,
                description = descriptionPart.body,
                categories = categories,
                productImage = imagePart,
                purchasePrice = purchasePricePart.body,
                rentPrice = rentPricePart.body,
                rentOption = rentOptionPart.body
            )

        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            _deleteResult.value = ResultWrapper.Loading
            _deleteResult.value = deleteProductUseCase(id)
        }
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

    suspend fun logout() {
        logoutUseCase.invoke()
    }

    // Title
    fun saveTitle(title: String) {
        CreateProductJourneySingleton.updateTitle(title = title)
    }

    fun getTitle(): String {
        return CreateProductJourneySingleton.getTitle()
    }

    // Categories
    fun saveCategories(categories: List<CategoryResponse>) {
        CreateProductJourneySingleton.updateCategories(categories)
    }

    fun getSavedCategories(): List<CategoryResponse> {
        return CreateProductJourneySingleton.getCategories()
    }

    // Description
    fun saveDescription(description: String) {
        CreateProductJourneySingleton.updateDescription(description = description)
    }

    fun getDescription(): String {
        return CreateProductJourneySingleton.getDescription()
    }

    // Images
    fun saveImages(images: List<Uri>) {
        CreateProductJourneySingleton.addImageUris(uris = images)
    }

    fun getImages(): List<Uri> {
        return CreateProductJourneySingleton.imageUris
    }

    // Price
    // Purchase Price
    fun savePurchasePrice(value: String) {
        CreateProductJourneySingleton.updatePurchasePrice(value)
    }

    fun getPurchasePrice(): String {
        return CreateProductJourneySingleton.getPurchasePrice()
    }

    // Rent Price
    fun saveRentPrice(value: String) {
        CreateProductJourneySingleton.updateRentPrice(value)
    }

    fun getRentPrice(): String {
        return CreateProductJourneySingleton.getRentPrice()
    }

    // Rent Option
    fun saveRentOption(value: String) {
        CreateProductJourneySingleton.updateRentOption(value)
    }

    fun getRentOption(): String {
        return CreateProductJourneySingleton.getRentOption()
    }

    fun clearAllInput() {
        return CreateProductJourneySingleton.clearAll()
    }

    // Cached Entities
    /*private var _cachedProduct: List<ProductEntity> = listOf()
    val cachedProduct: List<ProductEntity> = _cachedProduct

    suspend fun getCacheProducts(): List<ProductEntity> {
        _cachedProduct = getCacheProductLocallyUseCase()
        return _cachedProduct
    }

    suspend fun setCacheProduct(entity: ProductEntity) {
        cacheProductLocallyUseCase.invoke(entity)
    }

    suspend fun clearCache() {
        clearCacheProductUseCase()
    }
*/
}