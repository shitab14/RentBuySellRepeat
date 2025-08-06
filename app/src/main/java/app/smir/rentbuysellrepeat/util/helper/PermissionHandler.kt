package app.smir.rentbuysellrepeat.util.helper

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
/**
Created by Shitab Mir on 6/8/25.
shitabmir@gmail.com
 **/

class PermissionHandler {

    // Callback for a single permission request
    interface SinglePermissionCallback {
        fun onPermissionGranted(permission: String)
        fun onPermissionDenied(permission: String)
    }

    // Callback for a multiple permissions request
    interface MultiplePermissionsCallback {
        fun onPermissionsResult(results: Map<String, Boolean>)
    }

    private val permissionRegistry: PermissionRegistry
    private val component: Any // Can be a Fragment or an Activity
    private val context: Context

    private var singleCallback: SinglePermissionCallback? = null
    private var multipleCallback: MultiplePermissionsCallback? = null

    constructor(fragment: Fragment) {
        this.component = fragment
        this.context = fragment.requireContext()
        this.permissionRegistry = PermissionRegistry(fragment)
    }

    constructor(activity: ComponentActivity) {
        this.component = activity
        this.context = activity
        this.permissionRegistry = PermissionRegistry(activity)
    }

    // This nested class manages the launchers
    private inner class PermissionRegistry(owner: Any) {
        val singlePermissionLauncher = when (owner) {
            is Fragment -> owner.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                singleCallback?.let { callback ->
                    val permission = callback.javaClass.simpleName.replace("Callback", "")
                    if (isGranted) callback.onPermissionGranted(permission) else callback.onPermissionDenied(permission)
                }
            }
            is ComponentActivity -> owner.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                singleCallback?.let { callback ->
                    val permission = callback.javaClass.simpleName.replace("Callback", "")
                    if (isGranted) callback.onPermissionGranted(permission) else callback.onPermissionDenied(permission)
                }
            }
            else -> throw IllegalArgumentException("Owner must be a Fragment or ComponentActivity")
        }

        val multiplePermissionsLauncher = when (owner) {
            is Fragment -> owner.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
                multipleCallback?.onPermissionsResult(results)
            }
            is ComponentActivity -> owner.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
                multipleCallback?.onPermissionsResult(results)
            }
            else -> throw IllegalArgumentException("Owner must be a Fragment or ComponentActivity")
        }
    }

    // Public function to request a single permission
    fun requestPermission(permission: String, callback: SinglePermissionCallback) {
        this.singleCallback = callback

        when {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                callback.onPermissionGranted(permission)
            }
            shouldShowRationale(permission) -> {
                showPermissionRationaleDialog(permission)
            }
            else -> {
                permissionRegistry.singlePermissionLauncher.launch(permission)
            }
        }
    }

    // Public function to request multiple permissions
    fun requestMultiplePermissions(permissions: Array<String>, callback: MultiplePermissionsCallback) {
        this.multipleCallback = callback
        permissionRegistry.multiplePermissionsLauncher.launch(permissions)
    }

    // --- Helper methods for handling rationale and dialogs ---
    private fun showPermissionRationaleDialog(permission: String) {
        AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage("This app needs $permission access to proceed.")
            .setPositiveButton("OK") { _, _ ->
                permissionRegistry.singlePermissionLauncher.launch(permission)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                singleCallback?.onPermissionDenied(permission)
            }
            .show()
    }

    private fun shouldShowRationale(permission: String): Boolean {
        return when (component) {
            is Fragment -> component.shouldShowRequestPermissionRationale(permission)
            is ComponentActivity -> ActivityCompat.shouldShowRequestPermissionRationale(component, permission)
            else -> false
        }
    }
}