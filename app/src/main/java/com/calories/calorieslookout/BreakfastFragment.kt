package com.calories.calorieslookout

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.calories.calorieslookout.adapter.GridAdapter
import com.calories.calorieslookout.databinding.FragmentBreakfastBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth


class BreakfastFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()
    private var _binding: FragmentBreakfastBinding? = null
    private lateinit var binding:FragmentBreakfastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        (requireActivity() as LoginActivity).bottomNavigation.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBreakfastBinding.inflate(inflater)
        binding=_binding!!

        binding.lifecycleOwner = this
        binding.overViewModel = viewModel
        binding.itemGrid.adapter = GridAdapter()

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                binding.search.queryHint = "Search"
                binding.search.clearFocus()
                binding.search.onActionViewCollapsed()
                if(queryText.isNotEmpty()) {
                    viewModel.mealsSearch(queryText)
                }
                return true
            }

            override fun onQueryTextChange(queryText: String): Boolean {
                if(queryText.isNotEmpty()) {
                    viewModel.mealsSearch(queryText)
                }
                return true
            }
            })
        (context as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.breakfast -> {
                Log.e("test", "onOptionsItemSelected: breakfast")
                viewModel.getMealsPhotos("breakfast")
                return true
            }

            R.id.lunch -> {
                viewModel.getMealsPhotos("lunch")
                return true
            }

            R.id.dinner -> {
                viewModel.getMealsPhotos("dinner")
                return true
            }

            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                var action = BreakfastFragmentDirections.actionBreakfastFragment2ToLoginFragment()
                findNavController().navigate(action)
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}