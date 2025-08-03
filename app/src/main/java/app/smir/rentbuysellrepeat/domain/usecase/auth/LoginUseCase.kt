package app.smir.rentbuysellrepeat.domain.usecase.auth

import app.smir.rentbuysellrepeat.data.model.auth.LoginRequest
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.domain.repository.AuthRepository
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
class LoginUseCase @Inject constructor(
 private val repository: AuthRepository
) {
 suspend operator fun invoke(request: LoginRequest): ResultWrapper<Response<LoginResponse>> {
  return repository.login(request)
 }
}