package app.smir.rentbuysellrepeat.presentation.feature.auth

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.smir.rentbuysellrepeat.data.model.auth.LoginRequest
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.data.model.auth.RegisterResponse
import app.smir.rentbuysellrepeat.domain.usecase.auth.LoginUseCase
import app.smir.rentbuysellrepeat.domain.usecase.auth.RegisterUseCase
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AuthViewModel @Inject constructor(
 private val loginUseCase: LoginUseCase,
 private val registerUseCase: RegisterUseCase
) : ViewModel() {

 private val _loginResult = MutableLiveData<ResultWrapper<Response<LoginResponse>>>()
 val loginResult: LiveData<ResultWrapper<Response<LoginResponse>>> = _loginResult

 private val _registerResult = MutableLiveData<ResultWrapper<Response<RegisterResponse>>>()
 val registerResult: LiveData<ResultWrapper<Response<RegisterResponse>>> = _registerResult

 fun login(email: String, password: String, fcmToken: String) {
  viewModelScope.launch {
   _loginResult.value = ResultWrapper.Loading
   _loginResult.value = loginUseCase(LoginRequest(email, password, fcmToken))
  }
 }

 fun register(request: RegisterRequest) {
  viewModelScope.launch {
   _registerResult.value = ResultWrapper.Loading
   _registerResult.value = registerUseCase(request)
  }
 }
}