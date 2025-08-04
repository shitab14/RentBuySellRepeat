package app.smir.rentbuysellrepeat.presentation.feature.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import app.smir.rentbuysellrepeat.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import app.smir.rentbuysellrepeat.databinding.ActivityLoginBinding
import app.smir.rentbuysellrepeat.presentation.base.BaseActivity
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.LoadingDialog
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import app.smir.rentbuysellrepeat.util.validation.EmailValidator
import app.smir.rentbuysellrepeat.util.validation.PasswordValidator
import androidx.core.graphics.toColorInt
import app.smir.rentbuysellrepeat.presentation.feature.product.MyProductsActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {

    private val viewModel: AuthViewModel by viewModel()
    private lateinit var loadingDialog: LoadingDialog

    override fun setupViews() {
        loadingDialog = LoadingDialog(this)
        setupClickListeners()
        makeSignUpTextSpannableText()
    }

    override fun setupObservers() {
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is ResultWrapper.Loading -> showLoading()
                is ResultWrapper.Success -> {
                    hideLoading()
                    navigateToMyProducts()
                }
                is ResultWrapper.Error -> {
                    hideLoading()
                    binding.root.showSnackBar(result.message ?: "Login failed")
                }

                ResultWrapper.Empty -> {
                    hideLoading()
                    binding.root.showSnackBar("Login failed")
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            if (validateInputs()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.login(email, password, "dummy_fcm_token")
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateInputs(): Boolean {
        val emailValidator = EmailValidator()
        val passwordValidator = PasswordValidator()

        val isEmailValid = emailValidator.validate(binding.etEmail.text.toString())
        val isPasswordValid = passwordValidator.validate(binding.etPassword.text.toString())

        binding.tilEmail.error = if (!isEmailValid) "Invalid email" else null
        binding.tilPassword.error = if (!isPasswordValid) "Password must be at least 6 characters" else null

        return isEmailValid && isPasswordValid
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnLogin.isEnabled = true
    }

    private fun navigateToMyProducts() {
        startActivity(Intent(this, MyProductsActivity::class.java))
        finishAffinity()
    }

    private fun makeSignUpTextSpannableText() {
        val fullText = getString(R.string.don_t_have_account_sign_up)

        val spannableString = SpannableString(fullText)

        val startIndex = 19
        val endIndex = startIndex + "Sign up".length

        val customColor = getColor(R.color.purple_500)//"#673AB7".toColorInt()
        val colorSpan = ForegroundColorSpan(customColor)

        if (/*startIndex >= 0 && */startIndex < fullText.length && endIndex <= fullText.length) {
            spannableString.setSpan(
                colorSpan,
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvSignUp.text = spannableString
    }
}