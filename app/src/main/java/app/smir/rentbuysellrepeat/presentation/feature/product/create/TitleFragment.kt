package app.smir.rentbuysellrepeat.presentation.feature.product.create

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.smir.rentbuysellrepeat.databinding.FragmentCreateProductTitleBinding
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel
import app.smir.rentbuysellrepeat.util.extension.showSnackBar

class TitleFragment : Fragment() {

    private var _binding: FragmentCreateProductTitleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProductTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        //
    }

    fun getProductTitle(): String {
        return binding.etProductTitle.text.toString()
    }

    fun validate(): Boolean {
        return if (binding.etProductTitle.text.isNullOrEmpty()) {
            binding.root.showSnackBar("Please enter a title")
            false
        } else {
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}