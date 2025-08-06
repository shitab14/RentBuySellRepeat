package app.smir.rentbuysellrepeat.presentation.feature.product.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.smir.rentbuysellrepeat.data.model.product.CategoryResponse
import app.smir.rentbuysellrepeat.databinding.FragmentCategoriesBinding
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.network.ResultWrapper
import com.google.android.material.chip.Chip

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        viewModel.loadCategories()
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    // First, clear any existing chips to avoid duplicates on data refresh
                    binding.chipGroupCategories.removeAllViews()

                    // Check if the data is not null
                    result.data.body()?.forEach { category ->
                        val chip = Chip(binding.root.context).apply {
                            text = category.value
                            isCheckable = true
                            isClickable = true
                            id = View.generateViewId()
                            tag = category.value
                        }

                        binding.chipGroupCategories.addView(chip)
                    }
                }
                is ResultWrapper.Error -> {
                    binding.root.showSnackBar(result.message ?: "Failed to load categories")
                }
                else -> {
                }
            }
        }
    }

    fun validate(): Boolean {
        return if (viewModel.getSavedCategories().isEmpty()) {
            binding.root.showSnackBar("Please select at least one category")
            false
        } else {
            viewModel.saveCategories(
                getSelectedChipValues()
            )
            true
        }
    }

    fun getSelectedChipValues(): MutableList<CategoryResponse> {
        val chipGroup = binding.chipGroupCategories
        val selectedChipIds = chipGroup.checkedChipIds

        val selectedChips = mutableListOf<CategoryResponse>()

        selectedChipIds.forEach { chipId ->
            val chip = chipGroup.findViewById<Chip>(chipId)

            selectedChips.add(
                CategoryResponse(
                label = chip.text.toString(),
                value = chip.tag.toString()
                )
            )
//            (chip.tag as? Int)?.let { selectedCategoryIds.add(it) }
        }
        return selectedChips
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}