package app.smir.rentbuysellrepeat.presentation.feature.product.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.smir.rentbuysellrepeat.databinding.FragmentDescriptionBinding
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun getProductDescription(): String {
        return binding.etDescription.text.toString()
    }

    fun validate(): Boolean {
        return if (binding.etDescription.text.isNullOrEmpty()) {
//            binding.root.showSnackBar("Please enter a description")
            false
        } else {
            true
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveDescription(getProductDescription())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}