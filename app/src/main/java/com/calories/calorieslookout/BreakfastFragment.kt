package com.calories.calorieslookout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.calories.calorieslookout.adapter.GridAdapter
import com.calories.calorieslookout.databinding.FragmentBreakfastBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel


class BreakfastFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBreakfastBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.overViewModel = viewModel
        binding.itemGrid.adapter = GridAdapter()
        return binding.root
    }

}