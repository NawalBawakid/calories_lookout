package com.calories.calorieslookout

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.calories.calorieslookout.databinding.FragmentBreakfastDescriptionBinding
import com.calories.calorieslookout.databinding.FragmentCalculatorBinding
import com.calories.calorieslookout.viewModel.OverviewViewModel
import kotlin.math.roundToInt

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private lateinit var binding: FragmentCalculatorBinding

    private val viewModel: OverviewViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalculatorBinding.inflate(inflater)
        binding=_binding!!

        binding = FragmentCalculatorBinding.inflate(inflater, container, false)

//        (context as AppCompatActivity).setSupportActionBar(binding.toolbar)
//        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cacculate.setOnClickListener{
            var weight = binding.weight.text
            var height = binding.height.text
            var age = binding.age.text

            if (!(TextUtils.isEmpty(weight.toString()))){
                if (!(TextUtils.isEmpty(height.toString()))){
                    if (!(TextUtils.isEmpty(age.toString()))){
                        var weights = binding.weight.text.toString().toDouble()
                        var heights = binding.height.text.toString().toDouble()
                        var ages = binding.age.text.toString().toDouble()

                        var chooseButton = binding.chooseButton.checkedRadioButtonId

                        // var maleCalories = ((10*weights) + (6.25*heights) - (5*ages) + 5)

                        // var femaleCalories = ((10*weights) + (6.25*heights) - (5*ages) - 161)

                        var total = when(chooseButton){
                            binding.male.id -> viewModel.maleCalories(weights, heights, ages)
                            else -> viewModel.femaleCalories(weights, heights, ages)
                        }
                        binding.amount.setText("You need: $total Calories per day")
                    } else{
                        binding.age.setError("Required")
                        binding.amount.setText(" ")
                    }
                }else {
                    binding.height.setError("Required")
                    binding.amount.setText(" ")
                }
            }else{
                binding.weight.setError("Required")
                binding.amount.setText(" ")
            }
//            else{
//                var weights = binding.weight.text.toString().toDouble()
//                var heights = binding.height.text.toString().toDouble()
//                var ages = binding.age.text.toString().toDouble()
//
//                var chooseButton = binding.chooseButton.checkedRadioButtonId
//
//                var maleCalories = ((10*weights) + (6.25*heights) - (5*ages) + 5)
//
//                var femaleCalories = ((10*weights) + (6.25*heights) - (5*ages) - 161)
//
//                var total = when(chooseButton){
//                    binding.male.id -> maleCalories
//                    else -> femaleCalories
//                }
//                binding.amount.setText("You need: $total Calories per day")
//            }


            //   binding.amount.setText("You need: $x Calories per day")

        }

    }

}