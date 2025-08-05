package app.smir.rentbuysellrepeat.di

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

import app.smir.rentbuysellrepeat.BuildConfig
import app.smir.rentbuysellrepeat.data.database.AppDatabase
import app.smir.rentbuysellrepeat.data.repository.AuthRepositoryImpl
import app.smir.rentbuysellrepeat.data.repository.ProductRepositoryImpl
import app.smir.rentbuysellrepeat.data.source.local.AuthLocalDataSource
import app.smir.rentbuysellrepeat.data.source.local.ProductLocalDataSource
import app.smir.rentbuysellrepeat.data.source.remote.AuthRemoteDataSource
import app.smir.rentbuysellrepeat.data.source.remote.ProductRemoteDataSource
import app.smir.rentbuysellrepeat.domain.repository.AuthRepository
import app.smir.rentbuysellrepeat.domain.repository.ProductRepository
import app.smir.rentbuysellrepeat.network.api.AuthApi
import app.smir.rentbuysellrepeat.network.api.ProductApi
import app.smir.rentbuysellrepeat.network.interceptor.AuthInterceptor
import app.smir.rentbuysellrepeat.util.helper.DataStoreManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import app.smir.rentbuysellrepeat.domain.usecase.auth.LoginUseCase
import app.smir.rentbuysellrepeat.domain.usecase.auth.RegisterUseCase
import app.smir.rentbuysellrepeat.domain.usecase.auth.SaveUserAuthDataFromRegisterUseCase
import app.smir.rentbuysellrepeat.domain.usecase.auth.SaveUserAuthDataUseCase
import app.smir.rentbuysellrepeat.domain.usecase.auth.UserLoggedInUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.GetProductsUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.GetProductDetailsUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.CreateProductUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.UpdateProductUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.DeleteProductUseCase
import app.smir.rentbuysellrepeat.domain.usecase.product.GetCategoriesUseCase
import app.smir.rentbuysellrepeat.presentation.feature.auth.AuthViewModel
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel
import app.smir.rentbuysellrepeat.presentation.feature.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

object AppModule {
    private val networkModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(get<AuthInterceptor>())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single { AuthInterceptor(get()) }
        single { get<Retrofit>().create(AuthApi::class.java) }
        single { get<Retrofit>().create(ProductApi::class.java) }
    }

    private val databaseModule = module {
        single { AppDatabase.getDatabase(androidContext()) }
        single { get<AppDatabase>().authDao() }
        single { get<AppDatabase>().productDao() }
    // TODO: SHITAB will add product DAO methods here
    }

    private val dataSourceModule = module {
        single { AuthRemoteDataSource(get()) }
        single { AuthLocalDataSource(get(), get()) }
        single { ProductRemoteDataSource(get()) }
        single { ProductLocalDataSource(get()) }
    }

    private val repositoryModule = module {
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
        single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
    }

    private val useCaseModule = module {
        single { LoginUseCase(repository = get()) }
        single { SaveUserAuthDataUseCase(repository = get()) }
        single { RegisterUseCase(repository = get()) }
        single { SaveUserAuthDataFromRegisterUseCase(repository = get()) }
        single { GetProductsUseCase(repository = get()) }
        single { GetProductDetailsUseCase(repository = get()) }
        single { UserLoggedInUseCase(repository = get()) }
        single { CreateProductUseCase(repository = get()) }
        single { UpdateProductUseCase(repository = get()) }
        single { DeleteProductUseCase(repository = get()) }
        single { GetCategoriesUseCase(repository = get()) }
    }

    private val utilityModule = module {
        single { DataStoreManager(androidContext()) }
    }

    private val viewModelModule = module {
//        single { AuthViewModel(get(), get()) }
//        viewModelOf(::AuthViewModel)
        viewModel {
            SplashViewModel(
                userLoggedInUseCase = get()
            )
        }
        viewModel {
            AuthViewModel(
                loginUseCase = get(),
                registerUseCase = get(),
                saveUserAuthDataUseCase = get(),
                saveUserAuthDataForRegisterUseCase = get(),
            )
        }
        viewModel {
            ProductViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) }

    }


    val all = listOf(
        networkModule,
        databaseModule,
        dataSourceModule,
        repositoryModule,
        useCaseModule,
        utilityModule,
        viewModelModule
    )
}