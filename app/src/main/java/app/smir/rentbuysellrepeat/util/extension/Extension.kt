package app.smir.rentbuysellrepeat.util.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(messageRes), duration)
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(@StringRes messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    showSnackBar(context.getString(messageRes), duration)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.showConfirmationDialog(
    title: String,
    subtitle: String,
    yesText: String,
    noText: String,
    onYes: () -> Unit = {},
    onNo: () -> Unit = {}
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(subtitle)
        .setPositiveButton(yesText) { dialog, _ ->
            onYes()
            dialog.dismiss()
        }
        .setNegativeButton(noText) { dialog, _ ->
            dialog.dismiss()
            onNo()
        }
        .show()
}