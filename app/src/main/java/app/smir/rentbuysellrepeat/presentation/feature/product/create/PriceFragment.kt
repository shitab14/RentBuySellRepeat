package app.smir.rentbuysellrepeat.presentation.feature.product.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.smir.rentbuysellrepeat.R
import app.smir.rentbuysellrepeat.databinding.FragmentPriceBinding
import app.smir.rentbuysellrepeat.presentation.feature.product.ProductViewModel
import app.smir.rentbuysellrepeat.util.extension.showSnackBar
import app.smir.rentbuysellrepeat.util.helper.AppLogger
import org.koin.androidx.scope.fragmentScope

class PriceFragment : Fragment() {

    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.e("SHITAB PriceFragment onViewCreated")
        setupViews()

    }

    override fun onStart() {
        super.onStart()

    }

    private fun setupViews() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rent_option_arrays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spRentOption.adapter = adapter
        }

        binding.spRentOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                viewModel.saveRentOption(selectedItem)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }

    fun getPurchasePrice(): String {
        return binding.etPurchasePrice.getText().toString()//.text.toString()
    }

    fun getRentPrice(): String {
        return binding.etRentPrice.getText().toString()
    }

    fun getRentOption(): String {
        return (if (binding.spRentOption.selectedItemPosition == 0) "hour" else "day")
    }

    fun validate(): Boolean {
        return when {
            binding.etPurchasePrice.text.isNullOrEmpty() && binding.etRentPrice.text.isNullOrEmpty() -> {
                binding.root.showSnackBar("Please enter a price value")
                false
            }
            /*binding.etPurchasePrice.text.isNullOrEmpty() -> {
                binding.root.showSnackBar("Please enter purchase price")
                false
            }
            binding.etRentPrice.text.isNullOrEmpty() -> {
                binding.root.showSnackBar("Please enter rent price")
                false
            }*/
            else -> true
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.savePurchasePrice(getPurchasePrice())
        viewModel.saveRentPrice(getRentPrice())
        viewModel.saveRentOption(getRentOption())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}