package app.smir.rentbuysellrepeat.data.model.auth

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
data class LoginResponse(
    val email: String,
    val password: String,
    val fcm_token: String
)