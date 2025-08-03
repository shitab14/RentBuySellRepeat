package app.smir.rentbuysellrepeat.presentation.base

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import app.smir.rentbuysellrepeat.util.helper.LoadingDialog

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var loadingDialog: LoadingDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    abstract fun setupViews()
    abstract fun setupObservers()

    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(requireContext())
        }
        loadingDialog?.show()
    }

    fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}