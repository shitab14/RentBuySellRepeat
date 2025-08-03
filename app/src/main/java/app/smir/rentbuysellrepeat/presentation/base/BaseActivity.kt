package app.smir.rentbuysellrepeat.presentation.base

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import app.smir.rentbuysellrepeat.util.helper.LoadingDialog

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected lateinit var binding: VB
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupObservers()
    }

    abstract fun setupViews()
    abstract fun setupObservers()

    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog?.show()
    }

    fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}