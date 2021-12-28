package com.calories.calorieslookout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.calories.calorieslookout.adapter.GridAdapter
import com.calories.calorieslookout.databinding.FragmentBreakfastBinding
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.databinding.GridItemBinding
import com.calories.calorieslookout.network.HitsItem
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth


class BreakfastFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()
    private var _binding: FragmentBreakfastBinding? = null
    private lateinit var binding:FragmentBreakfastBinding
    private var isFavorite = true
//    var mealList = viewModel.infoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  binding.bottomnav.visibility = View.VISIBLE
//        setHasOptionsMenu(true)
//
//        var adapter: ListAdapter = ListAdapter(this, android.R.layout.simple_list_item_1, mealList)
//        binding?.listView?.adapter = adapter
//
//       binding?.listView?.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
//
//        }
//        binding?.listView?.emptyView = binding?.noresult


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

      //  var searchItem: MenuItem = menu.(R.id.search)
//        val searchView = searchItem.actionView as SearchView
//        searchView.queryHint = "Search"


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
           // to show the calories num befor the comma only
        viewModel.calories.observe(this.viewLifecycleOwner,{
        })



//        binding?.disLike?.setOnClickListener{
//            if(isFavorite){
//                binding!!.like.isVisible
//                binding!!.disLike.visibility = View.GONE
//                //menuItem.isVisible = getString(R.string.logout)
//            }else{
//                isFavorite = false
//                binding!!.disLike.isVisible
//                binding!!.like.visibility = View.GONE
//            }
//        }

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

            R.id.calculation -> {
                val queryUrl: Uri = Uri.parse("https://apps.apple.com/sa/app/lifesum-healthy-eating/id286906691")
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                this?.startActivity(intent)
                return true
            }

            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                var action = BreakfastFragmentDirections.actionBreakfastFragment2ToLoginFragment()
                findNavController().navigate(action)

//                var action = BreakfastFragmentDirections.actionBreakfastFragmentToNavGraph2()
//                findNavController(R.id.overviewFragment).navigate(action)
                return true
            }

            else -> {
                Log.e("test", "onOptionsItemSelected: else")
                return super.onOptionsItemSelected(item)
            }
        }



//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu, menu)

//
//        var searchItem: MenuItem = menu.findItem(R.id.search)
//        val searchView = searchItem.actionView as SearchView
//        searchView.queryHint = "Search"
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(queryText: String): Boolean {
//                searchView.clearFocus()
//                searchView.onActionViewCollapsed()
//                viewModel.mealsSearch(queryText)
//
//                return true
//            }
//
//            override fun onQueryTextChange(queryText: String): Boolean {
//                viewModel.mealsSearch(queryText)
//                return true
//            }
//        })
    }

}