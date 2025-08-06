package app.smir.rentbuysellrepeat.util.helper

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.FileProvider
import app.smir.rentbuysellrepeat.R
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
Created by Shitab Mir on 6/8/25.
shitabmir@gmail.com
 **/

class AndroidImageHelper(private val context: Context) {

 // Creates a temporary file to store the photo taken by the camera
 fun createImageFile(): File {
  val timeStamp: String =
   SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
  val storageDir: File? = context.getExternalFilesDir("Pictures")
  return File.createTempFile(
   "JPEG_${timeStamp}_", /* prefix */
   ".jpg", /* suffix */
   storageDir /* directory */
  )
 }

 // Get a content URI for the photo file, which is safe to share with the camera app
 fun getUriForFile(file: File): Uri {
  return FileProvider.getUriForFile(
   context,
   "app.smir.rentbuysellrepeat.fileprovider",
   file
  )
 }

 fun setImageUri(context: Context, imageUri: Uri, imageView: ImageView) {
  Glide.with(context)
   .load(imageUri)
   .placeholder(R.drawable.ic_placeholder)
   .error(R.drawable.ic_placeholder)
   .thumbnail(0.1f)
   .into(imageView)
 }

}