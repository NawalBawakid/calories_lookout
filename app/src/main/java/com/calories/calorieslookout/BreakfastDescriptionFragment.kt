package com.calories.calorieslookout

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.databinding.FragmentLoginBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel


class BreakfastDescriptionFragment : Fragment() {

//    companion object{
//        const val SEARCH_PREFIX = "https://www.google.com/search?q="
//    }

    private var binding: FragmentBreakfastDescriptionBinding? = null

    private val viewModel: OverviewViewModel by activityViewModels()


    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//        getActivity().supportActionBar().hide()

        arguments.let {
            index = it!!.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentBreakfastDescriptionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item = viewModel.infoItem.value?.get(index)?.recipe?.url

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            mealViewModel = viewModel
            detailsBreakfastFragment = this@BreakfastDescriptionFragment

            viewModel.informationll(index)

            recipe.setOnClickListener {
                val queryUrl: Uri = Uri.parse("${item}")
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                context?.startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.breakfast -> {
                Log.e("test", "onOptionsItemSelected: breakfast")
                viewModel.getMealsPhotos("breakfast")
                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToBreakfastFragment2()
                findNavController().navigate(action)
                return true
            }

            R.id.lunch -> {
                viewModel.getMealsPhotos("lunch")
                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToBreakfastFragment2()
                findNavController().navigate(action)
                return true
            }

            R.id.dinner -> {
                viewModel.getMealsPhotos("dinner")
                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToBreakfastFragment2()
                findNavController().navigate(action)
                return true
            }

            else -> {
                Log.e("test", "onOptionsItemSelected: else")
                return super.onOptionsItemSelected(item)
            }
        }
    }


}