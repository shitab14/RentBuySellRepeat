package app.smir.rentbuysellrepeat.util.helper

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

class DataStoreManager(context: Context) {
 private val dataStore = context.dataStore

 companion object {
  val ACCESS_TOKEN = stringPreferencesKey("access_token")
  val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
  val USER_EMAIL = stringPreferencesKey("user_email")
  val USER_ID = intPreferencesKey("user_id")
  val FCM_TOKEN = stringPreferencesKey("fcm_token")
 }

 suspend fun saveAccessToken(token: String) {
  dataStore.edit { preferences ->
   preferences[ACCESS_TOKEN] = token
  }
 }

 suspend fun saveRefreshToken(token: String) {
  dataStore.edit { preferences ->
   preferences[REFRESH_TOKEN] = token
  }
 }

 suspend fun saveUserEmail(email: String) {
  dataStore.edit { preferences ->
   preferences[USER_EMAIL] = email
  }
 }

 suspend fun saveUserId(id: Int) {
  dataStore.edit { preferences ->
   preferences[USER_ID] = id
  }
 }

 suspend fun saveFcmToken(token: String) {
  dataStore.edit { preferences ->
   preferences[FCM_TOKEN] = token
  }
 }

 val accessToken: Flow<String?> = dataStore.data.map { preferences ->
  preferences[ACCESS_TOKEN]
 }

 val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
  preferences[REFRESH_TOKEN]
 }

 val userEmail: Flow<String?> = dataStore.data.map { preferences ->
  preferences[USER_EMAIL]
 }

 val userId: Flow<Int?> = dataStore.data.map { preferences ->
  preferences[USER_ID]
 }

 val fcmToken: Flow<String?> = dataStore.data.map { preferences ->
  preferences[FCM_TOKEN]
 }

 suspend fun clearData() {
  dataStore.edit { preferences ->
   preferences.clear()
  }
 }

 fun getAccessToken(): String? {
  return runBlocking { accessToken.firstOrNull() }
 }

}