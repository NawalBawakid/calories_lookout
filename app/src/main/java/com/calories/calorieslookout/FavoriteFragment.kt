package com.calories.calorieslookout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.calories.calorieslookout.adapter.FavoriteAdapter
import com.calories.calorieslookout.adapter.GridAdapter

import com.calories.calorieslookout.databinding.FavoriteItemBinding
import com.calories.calorieslookout.databinding.FragmentBreakfastBinding
import com.calories.calorieslookout.databinding.FragmentFavoriteBinding
import com.calories.calorieslookout.databinding.GridItemBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder


class FavoriteFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()


    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var binding:FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater)
        binding = _binding!!

        binding.lifecycleOwner = this
        binding.overViewModel = viewModel
        binding.favoriteItem.adapter = FavoriteAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // to show the calories num befor the comma only
//        viewModel.calories.observe(this.viewLifecycleOwner, {
//        })

       viewModel.retriveData()


    }



}