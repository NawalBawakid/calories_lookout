package com.calories.calorieslookout

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide
import com.calories.calorieslookout.adapter.bindImage
import com.calories.calorieslookout.database.ProfileImage
import com.calories.calorieslookout.databinding.FragmentProfileBinding
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


class ProfileFragment : PreferenceFragmentCompat() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var binding: FragmentProfileBinding

//    private lateinit var imageUri: Uri
//    private lateinit var mFirestore: FirebaseFirestore
    private val viewModel: OverviewViewModel by activityViewModels()
    private val imageUrl: MutableList<ProfileImage> = mutableListOf()
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val switch: Switch? = null
    private lateinit var saveSettingData: SaveSettingData

  //  var userImage = viewModel.userImages

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
        //var item = viewModel.infoItem.value?.get(index)?.recipe?.url

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            mealViewModel = viewModel
            profileFragment = this@ProfileFragment

        }
        viewModel.profileImage.observe(viewLifecycleOwner,{
            bindImage(binding.profileImage,it)
        })

     //   profilePicture()


        viewModel.imagesScope()



        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.selectImage.setOnClickListener {
            launchGallery()
        }
        binding.editImage.setOnClickListener {
            uploadImage()

        }



//        saveSettingData = SaveSettingData(context)
//        if (saveSettingData.loadDarkModeState() == true){
//         //   setTheme(R.style.darkTheme)
//            getContext()?.getTheme()?.applyStyle(R.style.darkTheme, true)
//        }else{
//           // setTheme(R.style.AppTheme)
//            getContext()?.getTheme()?.applyStyle(R.style.AppTheme, false)
//        }

        binding.switchBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               // viewModel.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


//        binding.switchBtn.setOnClickListener{
//            if (saveSettingData.loadDarkModeState() == true){
//                getContext()?.getTheme()?.applyStyle(R.style.darkTheme, true)
//                switch?.isChecked = true
//            }else{
//                getContext()?.getTheme()?.applyStyle(R.style.AppTheme, false)
//            }
//
//            switch?.setOnCheckedChangeListener { _, isChecked ->
//                if (isChecked){
//                    saveSettingData.setDarkModeState(true)
//                    restartApplication()
//                }else{
//                    saveSettingData.setDarkModeState(false)
//                    restartApplication()
//                }
//            }
//        }

//        binding.userWeight.text = viewModel.weights.toString()
//        binding.userHeight.text = viewModel.heights.toString()
//        binding.userAge.text = viewModel.ages.toString()
    }

    override fun onResume() {
        super.onResume()
     //   viewModel.imagesScope()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //setPreferencesFromResource(R.xml.)
    }

    fun profilePicture(){
        var userImage = FirebaseAuth.getInstance().currentUser?.photoUrl
        Glide.with(this)
            .load(userImage)
            .fitCenter()
            .placeholder(R.drawable.calorieslogo)
            .into(binding.profileImage)
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
//                val bitmap = MediaStore.Images.Media.getBitmap(Application().contentResolver, filePath)
//                uploadImage().setImageBitmap(bitmap)
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
                Log.d("TAG", "addUploadRecordToDb: ${it.isSuccessful}")
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
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{
           // Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun restartApplication(){
        val restart = Intent(context, ProfileFragment::class.java)
        startActivity(restart)

    }

    fun retriveImage() {

        val db = FirebaseFirestore.getInstance()

        val imageRef = db.collection("posts")
        Log.e("TAG", "retriveImage: ${imageRef}", )
        imageRef.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                Log.e("TAG", "exception when query post", e)
                return@addSnapshotListener
            }
            val images = snapshot.toObjects(ProfileImage::class.java)
            imageUrl.addAll(images)
            Log.e("TAG", "retriveImage: ${images} ", )

            //        val dataRef = db.collection("posts")
            // val source = Source.CATCHE
//        dataRef.addSnapshotListener { snapshot, e ->
//     dataRef.get().addOnCompleteListener { task ->
//         if (task.isSuccessful) {
//             // Document found in the offline cache
//             val document = task.result
        }
     }
 }



//    fun retriveData() {
//        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//        private val CaloriesDataCollection = Firebase.firestore.collection("CaloriesData")
//
//        CaloriesDataCollection.document("users").collection(userId).get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val item = mutableListOf<CaloriesData?>()
//                    for (data in task.result!!.documents) {
//                        val calories = data.toObject<CaloriesData>()
//                        item.add(calories!!)
//                    }
//                    Log.d("TAG", "retriveData: $item")
//                    _likeItem.value = item
////                binding.favoriteItem = item
//                } else {
//
//                }
//            }.addOnFailureListener {
//                println(it.message)
//            }
//    }

//    private fun uploadImage(){
//        val progresDialog = ProgressDialog(context)
//        progresDialog.setMessage("Uploading File...")
//        progresDialog.setCancelable(false)
//        progresDialog.show()
//
//        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val fileName = formatter.format(now)
//        val storageRefrence = FirebaseStorage.getInstance().getReference("images/$fileName")
//
//        storageRefrence.putFile(imageUri).addOnSuccessListener {
//            it.metadata?.reference?.downloadUrl?.addOnCompleteListener {task->
//                var imageUrl=task.result.toString()
//
//                 }
//            Log.d("TAG", "uploadImage: ${it.metadata?.reference?.downloadUrl}")
//            binding.profileImage.setImageURI(null)
//            if (progresDialog.isShowing) progresDialog.dismiss()
//
//        }.addOnFailureListener{
//            if (progresDialog.isShowing) progresDialog.dismiss()
//        }
//    }
//
//    fun selectImage(){
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//
//        startActivityForResult(intent, 100)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 100 && resultCode == RESULT_OK){
//            imageUri = data?.data!!
//            binding.profileImage.setImageURI(imageUri)
//        }
//    }














