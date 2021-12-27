package com.calories.calorieslookout

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.ListAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.calories.calorieslookout.adapter.GridAdapter
import com.calories.calorieslookout.databinding.FragmentBreakfastBinding
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.databinding.GridItemBinding
import com.calories.calorieslookout.network.HitsItem
import com.calories.calorieslookout.viewModel.OverviewViewModel


class BreakfastFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()
   private var binding: GridItemBinding? = null
//    var mealList = viewModel.infoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setHasOptionsMenu(true)
//
//        var adapter: ListAdapter = ListAdapter(this, android.R.layout.simple_list_item_1, mealList)
//        binding?.listView?.adapter = adapter
//
//        binding?.listView?.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
//
//        }
//        binding?.listView?.emptyView = binding?.noresult

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBreakfastBinding.inflate(inflater)

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
//
//

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // to show the calories num befor the comma only
        viewModel.calories.observe(this.viewLifecycleOwner,{
        })

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu, menu)
//
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
//    }

}