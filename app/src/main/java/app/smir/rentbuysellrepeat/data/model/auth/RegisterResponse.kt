package app.smir.rentbuysellrepeat.data.model.auth

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
data class RegisterResponse(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val address: String,
    val firebase_console_manager_token: String,
    val password: String,
    val date_joined: String
)