package app.smir.rentbuysellrepeat.network.interceptor

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import app.smir.rentbuysellrepeat.util.helper.DataStoreManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
 private val dataStoreManager: DataStoreManager
) : Interceptor {

 override fun intercept(chain: Interceptor.Chain): Response {
  val requestBuilder = chain.request().newBuilder()

  // TODO: SHITAB will add token management here
  val token = dataStoreManager.getAccessToken()
  if (!token.isNullOrEmpty()) {
   requestBuilder.addHeader("Authorization", "Bearer $token")
  }

  return chain.proceed(requestBuilder.build())
 }
}