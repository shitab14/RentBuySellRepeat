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
import app.smir.rentbuysellrepeat.presentation.feature.auth.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val dataStoreManager: DataStoreManager by inject()

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

        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthStatus()
        }, 2000)
    }

    private fun checkAuthStatus() {
        // TODO: SHITAB will add auth check logic here
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}