package app.smir.rentbuysellrepeat.domain.repository

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import app.smir.rentbuysellrepeat.data.model.auth.LoginRequest
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import retrofit2.Response

interface AuthRepository {
 suspend fun login(request: LoginRequest): ResultWrapper<Response<LoginResponse>>
 suspend fun register(request: RegisterRequest): ResultWrapper<Response<RegisterResponse>>
 suspend fun saveAuthData(response: LoginResponse)
 suspend fun clearAuthData()
 fun isUserLoggedIn(): Boolean
}