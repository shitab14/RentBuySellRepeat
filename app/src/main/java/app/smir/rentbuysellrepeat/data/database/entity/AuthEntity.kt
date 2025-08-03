package app.smir.rentbuysellrepeat.data.database.entity

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val firstName: String?,
    val lastName: String?,
    val address: String?,
    val phoneNumber: String?,
    val fcmToken: String
)