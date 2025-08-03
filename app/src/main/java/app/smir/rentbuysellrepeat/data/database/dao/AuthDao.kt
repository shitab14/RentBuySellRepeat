package app.smir.rentbuysellrepeat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.smir.rentbuysellrepeat.data.database.entity.AuthEntity
import kotlinx.coroutines.flow.Flow

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
@Dao
interface AuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthData(authEntity: AuthEntity)

    @Query("SELECT * FROM auth LIMIT 1")
    fun getAuthData(): Flow<AuthEntity?>

    @Query("DELETE FROM auth")
    suspend fun clearAuthData()
}