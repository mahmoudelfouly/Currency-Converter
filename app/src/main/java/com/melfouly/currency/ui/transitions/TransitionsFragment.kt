package com.melfouly.currency.ui.transitions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.melfouly.currency.databinding.FragmentTransitionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransitionsFragment : Fragment() {

    private lateinit var binding: FragmentTransitionsBinding
    private val viewModel: TransitionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        binding = FragmentTransitionsBinding.inflate(layoutInflater)

        clearTransitions()

        viewModel.getAllTransitions()

        viewModel.transitionList.observe(viewLifecycleOwner) { list ->
            list.forEach {
                binding.fromAmountValue.append(it.fromAmount.toString() + "\n")
                binding.fromSymbolValue.append(it.fromSymbol + "\n")
                binding.toAmountValue.append(it.toAmount.toString() + "\n")
                binding.toSymbolValue.append(it.toSymbol + "\n")
            }
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        Log.d("Transitions", "onPause: called ${binding.fromAmountValue.text.isNotBlank()}")
    }

    override fun onStop() {
        super.onStop()
        clearTransitions()
        Log.d("Transitions", "onStop: called ${binding.fromAmountValue.text.isNotBlank()}")
    }


    private fun clearTransitions() {
        binding.fromAmountValue.text = ""
        binding.fromSymbolValue.text = ""
        binding.toAmountValue.text = ""
        binding.toSymbolValue.text = ""
    }


}