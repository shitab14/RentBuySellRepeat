package app.smir.rentbuysellrepeat.data.source.local

import app.smir.rentbuysellrepeat.data.database.dao.AuthDao
import app.smir.rentbuysellrepeat.data.model.auth.LoginResponse
import app.smir.rentbuysellrepeat.util.helper.DataStoreManager
import javax.inject.Inject

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

class AuthLocalDataSource @Inject constructor(
 private val authDao: AuthDao,
 private val dataStoreManager: DataStoreManager
) {
 suspend fun saveAuthData(response: LoginResponse) {
  dataStoreManager.saveAccessToken(response.fcm_token ?: "")
  dataStoreManager.saveUserEmail(response.email)
 }

 suspend fun clearAuthData() {
  authDao.clearAuthData()
  dataStoreManager.clearData()
 }

 fun isUserLoggedIn(): Boolean {
  return !dataStoreManager.getAccessToken().isNullOrEmpty()
 }
}