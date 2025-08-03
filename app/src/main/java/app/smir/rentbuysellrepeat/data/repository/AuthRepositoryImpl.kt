package app.smir.rentbuysellrepeat.data.repository

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import app.smir.rentbuysellrepeat.data.model.auth.LoginRequest
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import app.smir.rentbuysellrepeat.data.source.local.AuthLocalDataSource
import app.smir.rentbuysellrepeat.data.source.remote.AuthRemoteDataSource
import app.smir.rentbuysellrepeat.domain.repository.AuthRepository
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun login(request: LoginRequest): ResultWrapper<Response<LoginResponse>> {
        return remoteDataSource.login(request)
    }

    override suspend fun register(request: RegisterRequest): ResultWrapper<Response<RegisterResponse>> {
        return remoteDataSource.register(request)
    }

    override suspend fun saveAuthData(response: LoginResponse) {
        // TODO: SHITAB will add auth data saving logic here
    }

    override suspend fun clearAuthData() {
        localDataSource.clearAuthData()
    }

    override fun isUserLoggedIn(): Boolean {
        // TODO: SHITAB will add auth data saving logic here
        return false
    }
}