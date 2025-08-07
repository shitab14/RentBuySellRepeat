package app.smir.rentbuysellrepeat.presentation.feature.product.create

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.smir.rentbuysellrepeat.databinding.FragmentImageBinding
import app.smir.rentbuysellrepeat.presentation.feature.product.CreateProductActivity
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.AndroidImageHelper
import app.smir.rentbuysellrepeat.util.helper.PermissionHandler
import java.io.File

class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()

    private var currentPhotoUri: Uri? = null

    private lateinit var imageHelper: AndroidImageHelper
    private lateinit var permissionHandler: PermissionHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionHandler = PermissionHandler(this@ImageFragment)
        imageHelper = AndroidImageHelper(requireContext())
    }

    override fun onStart() {
        super.onStart()
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.btnTakePhoto.setOnClickListener {
            requestCameraPermission()
        }

        binding.btnUploadPhoto.setOnClickListener {
//            (requireActivity() as? CreateProductActivity)?.openImagePicker()
            requestStoragePermission()
        }
    }

    fun getProductImages(): List<Uri> {
        return listOf()
    }

    private fun setupObservers() {
    }

    fun validate(): Boolean {
        /*return if (viewModel.productImageUri.value == null) {
            binding.root.showSnackBar("Please upload a product image")
            false
        } else {
            true
        }*/
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestCameraPermission() {
        permissionHandler.requestPermission(Manifest.permission.CAMERA, object : PermissionHandler.SinglePermissionCallback {
            override fun onPermissionGranted(permission: String) {
                Toast.makeText(requireContext(), "$permission granted!", Toast.LENGTH_SHORT).show()
                openCamera()
            }

            override fun onPermissionDenied(permission: String) {
                Toast.makeText(requireContext(), "$permission denied!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun requestStoragePermission() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        permissionHandler.requestMultiplePermissions(permissions, object : PermissionHandler.MultiplePermissionsCallback {
            override fun onPermissionsResult(results: Map<String, Boolean>) {
                // Check if all permissions were granted
                val allGranted = results.all { it.value }
                if (allGranted) {
                    Toast.makeText(requireContext(), "All permissions granted!", Toast.LENGTH_SHORT).show()
                    openGallery()
                } else {
                    val denied = results.filter { !it.value }.keys
                    Toast.makeText(requireContext(), "Denied: $denied", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun openCamera() {
//         Create a temporary file to save the camera output
        val photoFile: File = imageHelper.createImageFile()
        currentPhotoUri = imageHelper.getUriForFile(photoFile)

        takePhotoLauncher.launch(currentPhotoUri)

    }

    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            currentPhotoUri?.let { uri ->
                viewModel.saveImages(listOf(uri))
                setThumbnail(uri)
            }
        }
    }

    private fun openGallery() {
        pickImagesLauncher.launch("image/*")
    }

    private val pickImagesLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            // Add all selected URIs to the singleton
            viewModel.saveImages(uris)
            setThumbnail(uris.last())
        }
    }

    private fun setThumbnail(uri: Uri) {
        // TODO: SHITAB Multiple Image Support
        imageHelper.setImageUri(context = requireContext(), imageUri = uri, imageView = binding.ivProduct)
    }

    override fun onPause() {
        super.onPause()
        getProductImages().let { viewModel.saveImages(it) }

    }

}