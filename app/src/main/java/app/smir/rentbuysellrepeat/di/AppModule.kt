package app.smir.rentbuysellrepeat.di

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

import app.smir.rentbuysellrepeat.BuildConfig
import app.smir.rentbuysellrepeat.network.api.AuthApi
import app.smir.rentbuysellrepeat.network.api.ProductApi
import app.smir.rentbuysellrepeat.network.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
    }

    private val dataSourceModule = module {
    }

    private val repositoryModule = module {
    }

    private val useCaseModule = module {
    }

    private val utilityModule = module {
    }

    val all = listOf(
        networkModule,
        databaseModule,
        dataSourceModule,
        repositoryModule,
        useCaseModule,
        utilityModule
    )
}