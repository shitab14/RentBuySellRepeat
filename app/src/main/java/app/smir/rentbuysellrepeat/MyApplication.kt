package app.smir.rentbuysellrepeat

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import android.app.Application
import app.smir.rentbuysellrepeat.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

 override fun onCreate() {
  super.onCreate()
  initKoin()
 }

 private fun initKoin() {
  startKoin {
   androidContext(this@MyApplication)
   modules(AppModule.all)
  }
 }
}