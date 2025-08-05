package app.smir.rentbuysellrepeat.domain.usecase.auth

import app.smir.rentbuysellrepeat.domain.repository.AuthRepository
import javax.inject.Inject

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/
class LogoutUseCase @Inject constructor(
 private val repository: AuthRepository
) {
 suspend operator fun invoke() {
  repository.clearAuthData()
 }

}