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
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth



class BreakfastDescriptionFragment : Fragment() {

//    companion object{
//        const val SEARCH_PREFIX = "https://www.google.com/search?q="
//    }

    private var _binding: FragmentBreakfastDescriptionBinding? = null
    private lateinit var binding:FragmentBreakfastDescriptionBinding

    private val viewModel: OverviewViewModel by activityViewModels()

    private var isFavorite = true


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
        binding=_binding!!

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

        binding.disLike.setOnClickListener{
            if(isFavorite){
                binding.like.visibility = View.VISIBLE
                isFavorite = false
                binding.like.visibility = View.VISIBLE
                binding.disLike.visibility = View.GONE

               val displyData = viewModel.favorite(index)
                viewModel.addtoFirebase(displyData)

            }else{
                isFavorite = true
                binding.disLike.visibility = View.VISIBLE
                binding.like.visibility = View.GONE
            }
        }

        binding.like.setOnClickListener{
            if(isFavorite){
                binding.like.visibility = View.VISIBLE
                isFavorite = false
                binding.like.visibility = View.VISIBLE
                binding.disLike.visibility = View.GONE

                val displyData = viewModel.favorite(index)
                viewModel.addtoFirebase(displyData)
               // viewModel.removeData(displyData)

            }else{
                isFavorite = true
                binding.disLike.visibility = View.VISIBLE
                binding.like.visibility = View.GONE
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

            R.id.calculation -> {
                val queryUrl: Uri = Uri.parse("https://apps.apple.com/sa/app/lifesum-healthy-eating/id286906691")
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                this?.startActivity(intent)
                return true
            }

            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragment2ToLoginFragment()
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