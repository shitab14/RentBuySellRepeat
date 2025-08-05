package app.smir.rentbuysellrepeat.domain.usecase.auth

import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import app.smir.rentbuysellrepeat.domain.repository.AuthRepository
import javax.inject.Inject

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/
class SaveUserAuthDataFromRegisterUseCase @Inject constructor(
        private val repository: AuthRepository
) {
    suspend operator fun invoke(value: RegisterResponse) {
        val values = LoginResponse(
                password = "",
                email = value.email,
                fcm_token = value.firebase_console_manager_token
        )
        repository.saveAuthData(values)
    }
}
