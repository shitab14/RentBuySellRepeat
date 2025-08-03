package app.smir.rentbuysellrepeat.domain.usecase.auth

import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import app.smir.rentbuysellrepeat.domain.repository.AuthRepository
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
class RegisterUseCase @Inject constructor(
 private val repository: AuthRepository
) {
 suspend operator fun invoke(request: RegisterRequest): ResultWrapper<Response<RegisterResponse>> {
  return repository.register(request)
 }
}