package com.calories.calorieslookout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.calories.calorieslookout.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }


    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build())


    // Create and launch sign-in intent
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//        (activity as AppCompatActivity).supportActionBar?.hide()


//        (requireActivity() as AppCompatActivity).supportActionBar?.hide()



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signin.setOnClickListener{
            signInLauncher.launch(signInIntent)
//            if(signInLauncher == ){
//
//            }
        }

        binding.move.setOnClickListener{
//            var action =
////            holder.itemOfMovie.findNavController().navigate(action)
//            binding.move.findNavController().navigate(action)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

        //  return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            var action = LoginFragmentDirections.actionLoginFragmentToBreakfastFragment2()
            binding.signin.findNavController().navigate(action)
        } else {
//            var action = BreakfastFragmentDirections.actionBreakfastFragmentToLoginFragment()
//            binding.signin.findNavController().navigate(action)
            // test
        }
    }

}