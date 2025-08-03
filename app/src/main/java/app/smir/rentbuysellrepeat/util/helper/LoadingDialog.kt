package app.smir.rentbuysellrepeat.util.helper

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Window
import app.smir.rentbuysellrepeat.R
import androidx.core.graphics.drawable.toDrawable

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        setCancelable(false)
    }
}