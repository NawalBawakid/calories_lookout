package com.calories.calorieslookout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth


class BreakfastDescriptionFragment : Fragment() {

//    companion object{
//        const val SEARCH_PREFIX = "https://www.google.com/search?q="
//    }

    private var _binding: FragmentBreakfastDescriptionBinding? = null
    private lateinit var binding: FragmentBreakfastDescriptionBinding

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
        _binding = FragmentBreakfastDescriptionBinding.inflate(inflater)
        binding = _binding!!

        binding = FragmentBreakfastDescriptionBinding.inflate(inflater, container, false)

        (context as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item = viewModel.infoItem.value?.get(index)?.recipe?.url

        binding.apply {
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

//        binding.like.isClickable = isVisible
//        binding.disLike.isClickable = isVisible


//        viewModel.isFav.observe(this.viewLifecycleOwner,{
//        })
//
//        binding.like.setOnFocusChangeListener {buttonView , isFavorite ->
//
//            CaloriesData[item].Checked = isChecked
//
//        }

        binding.like.setOnClickListener {

            val displyData = viewModel.favorite(index, "")
            if (displyData.label?.let { it1 -> viewModel.isFavorit(label = it1) } == false) {

                    binding.like.setImageResource(R.drawable.ic_baseline_favorite_24)
                    binding.like.visibility = View.VISIBLE

                    viewModel.addtoFirebase(displyData)
            }
            else {
                binding.like.visibility = View.VISIBLE
                viewModel.removeData(displyData)

            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //menu.clear()
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.breakfast -> {
                Log.e("test", "onOptionsItemSelected: breakfast")
                viewModel.getMealsPhotos("breakfast")
                var action =
                    BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToBreakfastFragment2()
                findNavController().navigate(action)
                return true
            }

            R.id.lunch -> {
                viewModel.getMealsPhotos("lunch")
                var action =
                    BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToBreakfastFragment2()
                findNavController().navigate(action)
                return true
            }

            R.id.dinner -> {
                viewModel.getMealsPhotos("dinner")
                var action =
                    BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToBreakfastFragment2()
                findNavController().navigate(action)
                return true
            }

//            R.id.calculation -> {
//                val queryUrl: Uri = Uri.parse("https://apps.apple.com/sa/app/lifesum-healthy-eating/id286906691")
//                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
//                this?.startActivity(intent)
//                return true
//            }

            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                var action =
                    BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToLoginFragment()
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
    }


}