package app.smir.rentbuysellrepeat.presentation.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import app.smir.rentbuysellrepeat.databinding.ActivitySplashBinding
import app.smir.rentbuysellrepeat.util.helper.DataStoreManager
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import app.smir.rentbuysellrepeat.BuildConfig
import app.smir.rentbuysellrepeat.presentation.feature.auth.LoginActivity
import app.smir.rentbuysellrepeat.presentation.feature.product.MyProductsActivity
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
//    private val dataStoreManager: DataStoreManager by inject()

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(BuildConfig.FLAVOR.equals("dev")) {
            binding.root.showSnackBar("This is Dev Flavor", Snackbar.LENGTH_LONG)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                checkAuthStatus()
            }
        }, 2000)
    }

    private fun checkAuthStatus() {
        if (viewModel.isUserLoggedIn()) {
            startActivity(Intent(this, MyProductsActivity::class.java))
        }
        else startActivity(Intent(this, LoginActivity::class.java))

        finish()
    }

}