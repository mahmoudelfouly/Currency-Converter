package com.melfouly.currency.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.melfouly.currency.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var symbolsAdapter: ArrayAdapter<String>
    private var fromSpinnerValuePosition: Int = 0
    private var toSpinnerValuePosition: Int = 0
    private var textFieldValue: String = ""
    private var amount: Double = 1.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // TextField Compose
        binding.textfieldCompose.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                var textFieldState by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = textFieldState,
                    onValueChange = {
                        textFieldState = it
                        textFieldValue = it
                        if (it == "") {
                            amount = 1.0
                        } else {
                            viewModel.convertCurrency(
                                binding.fromSpinner.selectedItem.toString(),
                                binding.toSpinner.selectedItem.toString(),
                                it.toDouble()
                            )
                        }
                    },
                    label = {
                        Text(text = "ex: 10.5")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        Log.d("Instance", "HomeFragment ViewModel object: $viewModel")

        viewModel.getAllCurrencies()
        viewModel.getCurrenciesLocally()

        // When editText changes
        /*binding.fromEdittext.doOnTextChanged { text, _, _, _ ->
            if (text.toString() == "") {
                amount = 1.0
            } else {
                amount = text.toString().toDouble()
                viewModel.convertCurrency(
                    binding.fromSpinner.selectedItem.toString(),
                    binding.toSpinner.selectedItem.toString(),
                    amount
                )
            }

        }*/

        viewModel.currencyList.observe(viewLifecycleOwner) {
            if (it.success) {
                val symbols = it.symbols.keys.sorted()
                symbolsAdapter = ArrayAdapter(
                    requireActivity(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    symbols
                )
                binding.fromSpinner.adapter = symbolsAdapter
                binding.toSpinner.adapter = symbolsAdapter

            } else {
                Toast.makeText(
                    requireActivity(),
                    "Something went wrong, Please try again later",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (!::symbolsAdapter.isInitialized) {
            viewModel.localCurrencies.observe(viewLifecycleOwner) { localCurrencies ->
                symbolsAdapter = ArrayAdapter(
                    requireActivity(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    localCurrencies
                )
                binding.fromSpinner.adapter = symbolsAdapter
                binding.toSpinner.adapter = symbolsAdapter
            }
        }

        viewModel.convertedValue.observe(viewLifecycleOwner) {
            binding.convertedValue.text = it.result.toString()
            if (!it.success) {
                // Edittext will be disabled and show toast with error info.
                binding.textfieldCompose.isEnabled = false
                Toast.makeText(requireActivity(), it.error?.info, Toast.LENGTH_LONG).show()

            }
        }

        binding.fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fromSpinnerValuePosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                toSpinnerValuePosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // When ExchangeCurrency button clicked
        binding.exchangeButton.setOnClickListener {
            binding.fromSpinner.setSelection(toSpinnerValuePosition)
            binding.toSpinner.setSelection(fromSpinnerValuePosition)
            amount = textFieldValue.toDouble()

            if (textFieldValue == "") {
                amount = 1.0
            } else {
                viewModel.convertCurrency(
                    binding.fromSpinner.selectedItem.toString(),
                    binding.toSpinner.selectedItem.toString(),
                    amount
                )
            }
        }


        return binding.root
    }


}