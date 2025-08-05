package app.smir.rentbuysellrepeat.presentation.feature.splash

import androidx.lifecycle.ViewModel
import app.smir.rentbuysellrepeat.domain.usecase.auth.UserLoggedInUseCase
import javax.inject.Inject

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/
class SplashViewModel @Inject constructor(
    private val userLoggedInUseCase: UserLoggedInUseCase
): ViewModel() {

    suspend fun isUserLoggedIn(): Boolean {
        return userLoggedInUseCase.invoke()
    }

}