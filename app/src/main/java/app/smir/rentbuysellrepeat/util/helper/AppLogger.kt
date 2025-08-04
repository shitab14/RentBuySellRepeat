package app.smir.rentbuysellrepeat.util.helper

import android.util.Log
import app.smir.rentbuysellrepeat.BuildConfig

/**
Created by Shitab Mir on 4/8/25.
shitabmir@gmail.com
 **/

object AppLogger {

 private const val DEFAULT_TAG = "AppLogger"

 private fun getTag(tag: String?): String {
  return tag ?: DEFAULT_TAG
 }

 fun v(message: String, tag: String? = null) {
  if (BuildConfig.DEBUG) {
   Log.v(getTag(tag), message)
  }
 }

 fun d(message: String, tag: String? = null) {
  if (BuildConfig.DEBUG) {
   Log.d(getTag(tag), message)
  }
 }

 fun i(message: String, tag: String? = null) {
  if (BuildConfig.DEBUG) {
   Log.i(getTag(tag), message)
  }
 }

 fun w(message: String, tag: String? = null) {
  if (BuildConfig.DEBUG) {
   Log.w(getTag(tag), message)
  }
 }

 fun e(message: String, tag: String? = null, throwable: Throwable? = null) {
  if (BuildConfig.DEBUG) {
   if (throwable != null) {
    Log.e(getTag(tag), message, throwable)
   } else {
    Log.e(getTag(tag), message)
   }
  }
 }

}