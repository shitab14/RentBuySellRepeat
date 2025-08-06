package app.smir.rentbuysellrepeat.presentation.feature.product.create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.smir.rentbuysellrepeat.databinding.FragmentSummaryBinding
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel
import app.smir.rentbuysellrepeat.util.helper.AndroidImageHelper

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var imageHelper: AndroidImageHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageHelper = AndroidImageHelper(requireContext())
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        getDataAndShow()
    }

    private fun getDataAndShow() {
        viewModel.getTitle().let { it ->
            binding.tvTitleValue.text = it
        }

        binding.tvCategoriesValue.text =
            viewModel.getSavedCategories().joinToString(", ") { it.label }

        viewModel.getDescription().let { it ->
            binding.tvDescriptionValue.text = it
        }

        viewModel.getImages().let { uris ->
            if (uris.isEmpty()) {
                binding.ivProductImage.visibility = View.GONE
            } else {
                binding.ivProductImage.visibility = View.VISIBLE
                imageHelper.setImageUri(context = requireContext(), imageUri = uris.first(), imageView = binding.ivProductImage)
            }
        }

        if(viewModel.getPurchasePrice().isNotBlank()) {
            binding.tvPriceValue.text = viewModel.getPurchasePrice()
        } else {
            binding.tvPriceValue.visibility = View.GONE
            binding.tvPriceLabel.visibility = View.GONE
        }

        if(viewModel.getRentPrice().isNotBlank()) {
            binding.tvRentValue.text = "${viewModel.getRentPrice()} /${viewModel.getRentOption()}"
        } else {
            binding.tvRentValue.visibility = View.GONE
            binding.tvRentLabel.visibility = View.GONE
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}