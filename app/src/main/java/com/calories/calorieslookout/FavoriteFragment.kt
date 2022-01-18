package com.calories.calorieslookout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.calories.calorieslookout.adapter.FavoriteAdapter
import com.calories.calorieslookout.database.CaloriesData

import com.calories.calorieslookout.databinding.FragmentFavoriteBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth


class FavoriteFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()


    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var binding:FragmentFavoriteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
var index = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater)
        binding = _binding!!

        binding.lifecycleOwner = this
        binding.overViewModel = viewModel
        binding.favoriteItem.adapter = FavoriteAdapter {
            viewModel.removeData(it)
            //Toast.makeText(requireContext(), "${it.label} ", Toast.LENGTH_SHORT).show()
            var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            var itemLables = it.label
         // viewModel.unFavItem(userId, itemLables)
//            getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // to show the calories num befor the comma only
//        viewModel.calories.observe(this.viewLifecycleOwner, {
//        })

       viewModel.retriveData()
//        getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit()



    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.retriveData()
//        binding.favoriteItem.adapter = FavoriteAdapter{ viewModel.removeData(it)}
//    }



}