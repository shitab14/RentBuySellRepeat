package app.smir.rentbuysellrepeat.data.database

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.smir.rentbuysellrepeat.data.database.dao.AuthDao
import app.smir.rentbuysellrepeat.data.database.entity.AuthEntity

@Database(
 entities = [AuthEntity::class, /*ProductEntity::class*/], // TODO: ADD HERE -> add Entity classes after creating here
 version = 2,
 exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

 abstract fun authDao(): AuthDao
// abstract fun productDao(): ProductDao

 companion object {
  @Volatile
  private var instance: AppDatabase? = null

  fun getDatabase(context: Context): AppDatabase =
   instance ?: synchronized(this) {
    instance ?: buildDatabase(context).also { instance = it }
   }

  private fun buildDatabase(context: Context) =
   Room.databaseBuilder(
   context.applicationContext,
   AppDatabase::class.java,
   "rent_buy_sell_repeat_db"
  ).fallbackToDestructiveMigration(false)
    .build()
 }
}