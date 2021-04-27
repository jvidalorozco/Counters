package com.cornershop.counterstest.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentExampleBinding
import com.cornershop.counterstest.presentation.viewmodels.MainScreenViewModel
import com.cornershop.counterstest.presentation.viewmodels.SharedViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExampleFragment : Fragment() {
    private var _binding: FragmentExampleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExampleBinding.inflate(inflater, container, false)


        val strArrayDrinks = resources.getStringArray(R.array.drinks_array)
        addChip(binding.chipGroupDrinks, strArrayDrinks.toList())

        val strArrayFood = resources.getStringArray(R.array.food_array)
        addChip(binding.chipGroupFood, strArrayFood.toList())

        val strArrayMisc = resources.getStringArray(R.array.misc_array)
        addChip(binding.chipGroupMisc, strArrayMisc.toList())

        binding.btClose.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
    private fun addChip(chipGroup: ChipGroup, lstExamples: List<String>) {
        for (item in lstExamples) {
            val lChip = Chip(requireContext())
            lChip.text = item
            lChip.setOnClickListener {
                viewModel.text.postValue(item)
               findNavController().popBackStack()

            }
            chipGroup.addView(lChip, chipGroup.childCount - 1)
        }
    }

}