package app.smir.rentbuysellrepeat.util.helper

/**
Created by Shitab Mir on 7/8/25.
shitabmir@gmail.com
 **/
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object BioMetricUtil {

 /**
  * Checks if the device is capable of biometric authentication.
  * @param context The context of the application.
  * @return true if biometrics are supported and enrolled, false otherwise.
  */
 fun isBiometricSupported(context: Context): Boolean {
  val biometricManager = BiometricManager.from(context)
  return biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
 }

 /**
  * Displays a biometric authentication prompt to the user.
  * @param activity The FragmentActivity that will own the prompt.
  * @param title The title of the biometric prompt dialog.
  * @param subtitle The subtitle of the dialog.
  * @param negativeButtonText The text for the negative button.
  * @param onSuccess Callback for successful authentication.
  * @param onFailed Callback for failed authentication (e.g., mismatch).
  * @param onError Callback for authentication errors (e.g., too many attempts).
  */
 fun showBiometricPrompt(
  activity: FragmentActivity,
  title: String,
  subtitle: String,
  negativeButtonText: String,
  onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
  onFailed: () -> Unit,
  onError: (errorCode: Int, errString: CharSequence) -> Unit
 ) {
  val executor: Executor = Executors.newSingleThreadExecutor()

  val biometricPrompt = BiometricPrompt(activity, executor,
   object : BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
     super.onAuthenticationError(errorCode, errString)
     onError(errorCode, errString)
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
     super.onAuthenticationSucceeded(result)
     onSuccess(result)
    }

    override fun onAuthenticationFailed() {
     super.onAuthenticationFailed()
     onFailed()
    }
   })

  val promptInfo = BiometricPrompt.PromptInfo.Builder()
   .setTitle(title)
   .setSubtitle(subtitle)
   .setNegativeButtonText(negativeButtonText)
   .setAllowedAuthenticators(BIOMETRIC_STRONG)
   .build()

  biometricPrompt.authenticate(promptInfo)
 }
}