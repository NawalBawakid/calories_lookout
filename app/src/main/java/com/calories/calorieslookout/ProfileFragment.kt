package com.calories.calorieslookout

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.calories.calorieslookout.adapter.bindImage
import com.calories.calorieslookout.databinding.FragmentProfileBinding
import com.calories.calorieslookout.viewModel.CaloriesApiStatus.*
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*


class ProfileFragment : Fragment(){

    private var _binding: FragmentProfileBinding? = null
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: OverviewViewModel by activityViewModels()
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater)
        binding = _binding!!

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            mealViewModel = viewModel
            profileFragment = this@ProfileFragment

        }
        viewModel.profileImage.observe(viewLifecycleOwner,{
            bindImage(binding.profileImage,it)
        })


        viewModel.imagesScope()


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.selectImage.setOnClickListener {
            launchGallery()
        }

        binding.editImage.setOnClickListener {
           uploadImage()
        }


        binding.switchBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


    private fun launchGallery() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            if (data == null || data.data == null){
                return
            }
            filePath = data.data
            try {

            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }


    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()

        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts").document(userId).set(data)
            .addOnCompleteListener {
               if(it.isSuccessful){

                   viewModel.imagesScope()
               }
            }
    }


    private fun uploadImage(){
        if(filePath != null){
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                } else {
                }
            }?.addOnFailureListener{

            }
        }else{
        }
    }
 }
