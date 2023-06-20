package com.melfouly.currency

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import com.melfouly.currency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var symbolsAdapter: ArrayAdapter<String>
    private var fromSpinnerValuePosition: Int = 0
    private var toSpinnerValuePosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Instance", "MainActivity ViewModel object: $viewModel")

        viewModel.getAllCurrencies()
        viewModel.getCurrenciesLocally()

        // When editText changes
        binding.fromEdittext.doOnTextChanged { text, start, before, count ->
            viewModel.convertCurrency(
                binding.fromSpinner.selectedItem.toString(),
                binding.toSpinner.selectedItem.toString(),
                text.toString().toDouble()
            )
            Log.d("Main", "${binding.fromSpinner.selectedItem},\n" +
                    "${binding.toSpinner.selectedItem},\n" +
                    "${text.toString().toDouble()}")
        }

        viewModel.currencyList.observe(this) {
            if (it.success) {
                val symbols = it.symbols.keys.sorted()
                symbolsAdapter = ArrayAdapter(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    symbols
                )
                binding.fromSpinner.adapter = symbolsAdapter
                binding.toSpinner.adapter = symbolsAdapter

            } else {
                Toast.makeText(this, "Something went wrong, Please try again later", Toast.LENGTH_SHORT).show()
            }
        }

        if (!::symbolsAdapter.isInitialized) {
            viewModel.localCurrencies.observe(this) { localCurrencies ->
                symbolsAdapter = ArrayAdapter(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    localCurrencies
                )
                binding.fromSpinner.adapter = symbolsAdapter
                binding.toSpinner.adapter = symbolsAdapter
            }
        }

        viewModel.convertedValue.observe(this) {
            binding.convertedValue.text = it.result.toString()
            if (!it.success) {
                // Edittext will be disabled and show toast with error info.
                binding.fromEdittext.isEnabled = false
                Toast.makeText(this, it.error?.info, Toast.LENGTH_LONG).show()

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
            viewModel.convertCurrency(
                binding.fromSpinner.selectedItem.toString(),
                binding.toSpinner.selectedItem.toString(),
                binding.fromEdittext.text.toString().toDouble()
            )
        }

    }
}