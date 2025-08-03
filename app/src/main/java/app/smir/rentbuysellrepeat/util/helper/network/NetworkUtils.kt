package app.smir.rentbuysellrepeat.util.helper.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import okhttp3.Interceptor
import okhttp3.Response
/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    class NoConnectionInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!isNetworkAvailable(context)) {
                throw NoConnectivityException()
            }
            return chain.proceed(chain.request())
        }
    }

    class NoConnectivityException : Exception("No internet connection available")
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val message: String? = null, val code: Int? = null) : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
    object Empty : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (e: Exception) {
        when (e) {
            is NetworkUtils.NoConnectivityException -> ResultWrapper.Error("No internet connection")
            else -> ResultWrapper.Error(e.message ?: "Unknown error occurred")
        }
    }
}