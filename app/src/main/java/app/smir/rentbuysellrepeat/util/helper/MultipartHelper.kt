package app.smir.rentbuysellrepeat.util.helper

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source

/**
Created by Shitab Mir on 6/8/25.
shitabmir@gmail.com
 **/

object MultipartHelper {

 fun createTextPart(key: String, value: String): MultipartBody.Part {
  val requestBody = value.toRequestBody("text/plain".toMediaTypeOrNull())
  return MultipartBody.Part.createFormData(key, null, requestBody)
 }

 fun createIntPart(key: String, value: Int): MultipartBody.Part {
  val stringValue = value.toString()
  val requestBody = stringValue.toRequestBody("text/plain".toMediaTypeOrNull())
  return MultipartBody.Part.createFormData(key, null, requestBody)
 }

 suspend fun createFilePart(
  context: Context,
  key: String,
  uri: Uri
 ): MultipartBody.Part = withContext(Dispatchers.IO) {
  val contentResolver: ContentResolver = context.contentResolver
  val fileName = getFileName(contentResolver, uri) ?: "file_upload.jpg"
  val mimeType = contentResolver.getType(uri) ?: "image/*"

  val requestBody = object : RequestBody() {
   override fun contentType() = mimeType.toMediaTypeOrNull()
   override fun writeTo(sink: BufferedSink) {
    contentResolver.openInputStream(uri)?.use { inputStream ->
     sink.writeAll(inputStream.source())
    }
   }
  }
  return@withContext MultipartBody.Part.createFormData(key, fileName, requestBody)
 }

 // --- New methods for arrays ---

 /**
  * Creates a list of multipart parts from a list of strings.
  * The key is automatically appended with '[]' to signal an array to the server.
  */
 fun createPartsFromStrings(key: String, values: List<String>): List<MultipartBody.Part> {
  return values.map { value ->
   val requestBody = value.toRequestBody("text/plain".toMediaTypeOrNull())
   MultipartBody.Part.createFormData("${key}[]", null, requestBody)
  }
 }

 /**
  * Creates a list of multipart parts from a list of integers.
  * The key is automatically appended with '[]' to signal an array.
  */
 fun createPartsFromInts(key: String, values: List<Int>): List<MultipartBody.Part> {
  return values.map { value ->
   val requestBody = value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
   MultipartBody.Part.createFormData("${key}[]", null, requestBody)
  }
 }

 /**
  * Creates a list of image parts from a list of URIs.
  * This is a suspend function as it performs I/O for each URI.
  * The key is automatically appended with '[]'.
  */
 suspend fun createPartsFromUris(
  context: Context,
  key: String,
  uris: List<Uri>
 ): List<MultipartBody.Part> = withContext(Dispatchers.IO) {
  uris.map { uri ->
   async { createFilePart(context, "${key}[]", uri) }
  }.awaitAll()
 }

 // --- Helper function for file names (from previous version) ---

 private fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
  var name: String? = null
  val cursor = contentResolver.query(uri, null, null, null, null)
  cursor?.use {
   it.moveToFirst()
   val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
   if (nameIndex != -1) {
    name = it.getString(nameIndex)
   }
  }
  return name
 }
}