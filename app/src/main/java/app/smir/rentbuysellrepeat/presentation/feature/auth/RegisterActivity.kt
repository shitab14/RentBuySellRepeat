package app.smir.rentbuysellrepeat.presentation.feature.auth

import android.content.Intent
import android.view.View
import app.smir.rentbuysellrepeat.data.model.auth.RegisterRequest
import app.smir.rentbuysellrepeat.databinding.ActivityRegisterBinding
import app.smir.rentbuysellrepeat.presentation.base.BaseActivity
import app.smir.rentbuysellrepeat.presentation.feature.product.MyProductsActivity
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import app.smir.rentbuysellrepeat.util.validation.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(
    ActivityRegisterBinding::inflate
) {

    private val viewModel: AuthViewModel by viewModel()

    override fun setupViews() {
        setupClickListeners()
    }

    override fun setupObservers() {
        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is ResultWrapper.Loading -> showLoading()
                is ResultWrapper.Success -> {
                    result.data.body()?.let { viewModel.saveAuthDataOnRegistration(it) }

                    hideLoading()
                    navigateToMyProducts()
                }
                is ResultWrapper.Error -> {
                    hideLoading()
                    binding.root.showSnackBar(result.message ?: "Registration failed")
                }
                ResultWrapper.Empty -> {
                    hideLoading()
                    binding.root.showSnackBar("Registration failed")
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {

            if (validateInputs()) {
                val request = createRegisterRequest()
                viewModel.register(request)
            }

        }

        binding.tvSignIn.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    private fun createRegisterRequest(): RegisterRequest {
        return RegisterRequest(
            email = binding.etEmail.text.toString(),
            first_name = binding.etFirstName.text.toString(),
            last_name = binding.etLastName.text.toString(),
            address = binding.etAddress.text.toString(),
            firebase_console_manager_token = "dummy_token",
            password = binding.etPassword.text.toString()
        )
    }

    private fun validateInputs(): Boolean {
        val validators = listOf(
            EmailValidator().validate(binding.etEmail.text.toString()),
            NameValidator().validate(binding.etFirstName.text.toString()),
            NameValidator().validate(binding.etLastName.text.toString()),
            AddressValidator().validate(binding.etAddress.text.toString()),
            PasswordValidator().validate(binding.etPassword.text.toString()),
            ConfirmPasswordValidator().validate(
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString()
            )
        )

        // Set errors
        binding.tilEmail.error = if (!validators[0]) "Invalid email" else null
        binding.tilFirstName.error = if (!validators[1]) "Invalid first name" else null
        binding.tilLastName.error = if (!validators[2]) "Invalid last name" else null
        binding.tilAddress.error = if (!validators[3]) "Address required" else null
        binding.tilPassword.error = if (!validators[4]) "Password must be at least 6 characters" else null
        binding.tilConfirmPassword.error = if (!validators[5]) "Passwords don't match" else null

        return validators.all { it }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnRegister.isEnabled = false
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnRegister.isEnabled = true
    }

    private fun navigateToMyProducts() {
        startActivity(Intent(this, MyProductsActivity::class.java))
        finishAffinity()
    }
}