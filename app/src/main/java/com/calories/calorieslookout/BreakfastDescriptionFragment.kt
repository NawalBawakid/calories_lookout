package com.calories.calorieslookout

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
        arguments.let {
            index = it!!.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

}