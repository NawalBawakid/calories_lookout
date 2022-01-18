package com.calories.calorieslookout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth


class BreakfastDescriptionFragment : Fragment() {

    private var _binding: FragmentBreakfastDescriptionBinding? = null
    private lateinit var binding: FragmentBreakfastDescriptionBinding

    private val viewModel: OverviewViewModel by activityViewModels()


    var index = 0
    var favorite = 0
    var like = false
    lateinit var userId: String
    lateinit var itemLables: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            index = it!!.getInt("id")
            favorite = it!!.getInt("favorite")
        }
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        itemLables = viewModel.infoItem.value?.get(index)?.recipe?.label.toString()

        like = viewModel.isFav(userId, itemLables)
        Log.e("TAG", "onCreate: $like")




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

        isLike(like)

        var item = viewModel.infoItem.value?.get(index)?.recipe?.url

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            mealViewModel = viewModel
            detailsBreakfastFragment = this@BreakfastDescriptionFragment


            if (favorite == 1) {
//                viewModel.favorite(index, "" )
                viewModel.informationll(index, 1)
            } else {
                viewModel.informationll(index)
            }

            recipe.setOnClickListener {
                val queryUrl: Uri = Uri.parse("${item}")
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                context?.startActivity(intent)
            }
        }


        binding.like.setOnClickListener {


            // var favItem = viewModel.likeItem.value?.get(index)?.label
            // Log.d("TAG", "onViewCreated: $favItem")

            // viewModel.favItem(userId, itemLable)

           viewModel.favItem(userId, itemLables)



            isLike(like)
            if (viewModel.isFav("", itemLables) == false) {
                binding.like.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.like.visibility = View.VISIBLE


            } else {
                binding.like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                binding.like.visibility = View.VISIBLE
                //viewModel.removeData(displyData)
             viewModel.unFavItem(userId, itemLables)

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


    fun isLike(fav: Boolean){
        if (fav) {
            binding.like.setImageResource(R.drawable.ic_baseline_favorite_24)
            binding.like.visibility = View.VISIBLE

        } else {
            binding.like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            binding.like.visibility = View.VISIBLE

        }
    }


}