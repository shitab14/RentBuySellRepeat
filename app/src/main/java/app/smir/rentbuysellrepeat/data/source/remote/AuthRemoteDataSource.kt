package app.smir.rentbuysellrepeat.data.source.remote

import app.smir.rentbuysellrepeat.data.model.auth.LoginRequest
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import app.smir.rentbuysellrepeat.network.api.AuthApi
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import app.smir.rentbuysellrepeat.util.helper.network.safeApiCall
import retrofit2.Response
import javax.inject.Inject
/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

class AuthRemoteDataSource @Inject constructor(
 private val authApi: AuthApi
) {
 suspend fun login(request: LoginRequest): ResultWrapper<Response<LoginResponse>> {
  return safeApiCall { authApi.login(request) }
 }

 suspend fun register(request: RegisterRequest): ResultWrapper<Response<RegisterResponse>> {
  return safeApiCall { authApi.register(request) }
 }
}