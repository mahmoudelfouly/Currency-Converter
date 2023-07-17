package com.melfouly.currency.ui.transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        viewModel.getAllTransitions()

        viewModel.transitionList.observe(viewLifecycleOwner) { list ->
            binding.rvCompose.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(list.size) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0XFF9AAEBB)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = list[it].fromAmount.toString(),
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = list[it].fromSymbol,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = list[it].toAmount.toString(),
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = list[it].toSymbol,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }


}