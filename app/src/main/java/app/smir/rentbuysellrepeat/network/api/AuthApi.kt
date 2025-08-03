package app.smir.rentbuysellrepeat.network.api

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import app.smir.rentbuysellrepeat.data.model.auth.LoginRequest
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/users/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/users/register/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}