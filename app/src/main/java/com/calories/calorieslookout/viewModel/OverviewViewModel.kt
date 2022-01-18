package com.calories.calorieslookout.viewModel

import android.icu.text.PluralRules.DEFAULT
import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.lifecycle.*
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.database.ProfileImage
import com.calories.calorieslookout.network.BreakfastApi
import com.calories.calorieslookout.network.HitsItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import java.security.spec.PSSParameterSpec.DEFAULT
import java.text.DateFormat.DEFAULT
import javax.xml.transform.Source


enum class CaloriesApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<CaloriesApiStatus>()
    val status: LiveData<CaloriesApiStatus> = _status

    private val _infoItem = MutableLiveData<List<HitsItem?>?>()
    var infoItem: LiveData<List<HitsItem?>?> = _infoItem

    private val _likeItem = MutableLiveData<MutableList<CaloriesData?>?>()
    var likeItem: LiveData<MutableList<CaloriesData?>?> = _likeItem

    private val _profileImage = MutableLiveData<String>()
    var profileImage: LiveData<String> = _profileImage

    private val _userImages = MutableLiveData<String>()
    val userImages : LiveData<String> = _userImages

    private val _photos = MutableLiveData<String>()
    val photos: LiveData<String> = _photos

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _isFav = MutableLiveData<String>()
    val isFav: LiveData<String> = _isFav

    private val _descriptions = MutableLiveData<String>()
    val descriptions: LiveData<String> = _descriptions

    private val _calories = MutableLiveData<String>()
    val calories: LiveData<String> = _calories

    private var _weights = MutableLiveData<Double>()
    val weights: LiveData<Double> = _weights

    private val _heights = MutableLiveData<Int>()
    val heights: LiveData<Int> = _heights

    private val _ages = MutableLiveData<Int>()
    val ages: LiveData<Int> = _ages

    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount: LiveData<Double> = _totalAmount

    private val _ingredient = MutableLiveData<String>()
    val ingredient: LiveData<String> = _ingredient

    private val CaloriesDataCollection = Firebase.firestore.collection("CaloriesData")
    private val ImageCollection = Firebase.firestore.collection("posts")

    private val _caloriesNum = MutableLiveData<Double>()
    val caloriesNum: LiveData<Double> = _caloriesNum

    var userNames = FirebaseAuth.getInstance().currentUser?.displayName

    var userEmail = FirebaseAuth.getInstance().currentUser?.email

    var userImage = FirebaseAuth.getInstance().currentUser?.photoUrl

    private val _foodId = MutableLiveData<String>()
    val foodId: LiveData<String> = _foodId

    private val _isChecked = MutableLiveData<Boolean>()
    var isChecked: LiveData<Boolean> = _isChecked

    private val imageUrl: MutableList<ProfileImage> = mutableListOf()


//    private val _isFavorite = MutableLiveData<Boolean>()
//    val isFavorite: LiveData<Boolean> = _isFavorite


    //    private val apiService = BreakfastApiService()
    private val mutableSearchTerm = MutableLiveData<String>()

    val favoritList: MutableList<CaloriesData> = mutableListOf()


    init {
        getMealsPhotos("breakfast")
      retriveData()
    }


    fun getMealsPhotos(type: String) {
        viewModelScope.launch {
            _status.value = CaloriesApiStatus.LOADING
            try {
                Log.e("try", "${_infoItem.value}")
                when (type) {
                    "breakfast" -> {
                        _infoItem.value = BreakfastApi.retrofitService.getPhotos("breakfast").hits
                        Log.e("br", "${_infoItem.value}")
                    }
                    "lunch" -> _infoItem.value =
                        BreakfastApi.retrofitService.getPhotos("lunch").hits
                    "dinner" -> _infoItem.value =
                        BreakfastApi.retrofitService.getPhotos("dinner").hits
                    else -> _infoItem.value =
                        BreakfastApi.retrofitService.getPhotos("breakfast").hits
                }
                _status.value = CaloriesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CaloriesApiStatus.ERROR
                _infoItem.value = listOf()
            }
        }
    }

    fun maleCalories(weights: Double, heights: Int, ages: Int): Double {

//        _weights.value = weights
//        _heights.value = heights
//        _ages.value = ages
        return ((10 * weights) + (6.25 * heights) - (5 * ages) + 5)
    }

    fun femaleCalories(weights: Double, heights: Int, ages: Int): Double {
//        _weights.value = weights
//        _heights.value = heights
//        _ages.value = ages
        return ((10 * weights) + (6.25 * heights) - (5 * ages) - 161)
    }

//    fun addUserDataToFirebase(itemFavorate: CaloriesData) {
//        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//        CaloriesDataCollection.document("profileInfo").collection(userId).add(itemFavorate)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                }
//            }
//    }


//    fun retriveImage() {
//
//        val db = FirebaseFirestore.getInstance()
//        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//
//        val imageRef = db.collection("posts").document(userId)
//        Log.e("TAG", "retriveImage: ${imageRef}", )
//        imageRef.addSnapshotListener { snapshot, e ->
//
//            Log.e("TAG", "retriveImage: $snapshot", )
//
//
//            if (e != null || snapshot == null) {
//                Log.e("TAG", "exception when query post", e)
//                return@addSnapshotListener
//            }
//          //  val images = snapshot.toObjects(ProfileImage::class.java)
//          //  imageUrl.addAll(images)
//           // Log.e("TAG", "retriveImage: ${images} ", )
//
//        }
//    }


    fun retriveImages() {
        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
      var a=  ImageCollection.document(userId).get()

        Log.e("TAG", "retriveImages: $a.", )
            a.addOnCompleteListener { task ->
                Log.e("TAG", "retriveImages: 1 ${task.result!!.data?.get("imageUrl")}")
                if (task.isSuccessful) {
                  //  val item = ProfileImage?>()

                        Log.e("TAG", "retriveImages: success${task.result!!.data}")

                  //  _profileImage.value = "item"
                    _profileImage.value = task.result!!.data?.get("imageUrl").toString()


                  //Log.d("TAG", "retriveImage: 4 $item")
//                    item = _userImages.value
//                binding.favoriteItem = item
                } else {

                }
            }.addOnFailureListener {
                println(it.message)
            }
    }

    fun imagesScope(){
        viewModelScope.launch {
            try {
                retriveImages()
            }catch(e: Exception){

            }
        }
    }



    fun informationll(index: Int, favorite: Int = 0) {
        if (favorite == 1) {
            favMealsList(index)
            retriveData()
        } else {
            detailsMealsList(index)
            retriveData()
        }

    }


    fun favMealsList(index: Int) {
        var item = _likeItem.value?.get(index)

        _photos.value = item?.image
        _title.value = item?.label
        _calories.value = item?.getCaloriesAsString()
        _ingredient.value = item?.ingredientLines.toString()
    }

    fun detailsMealsList(index: Int) {
        var item = _infoItem.value?.get(index)

        _photos.value = item?.recipe?.image
        _title.value = item?.recipe?.label
        _descriptions.value = item?.recipe?.url
        _calories.value = item?.recipe?.getCaloriesAsString()
        _ingredient.value = item?.recipe?.ingredientLines.toString()

    }


    fun mealsSearch(query: String) {
        viewModelScope.launch {
            _status.value = CaloriesApiStatus.LOADING
            try {
                when (query) {
                    query -> _infoItem.value = BreakfastApi.retrofitService.getPhotos(query).hits
                    else -> _infoItem.value =
                        BreakfastApi.retrofitService.getPhotos("breakfast").hits
                }
                _status.value = CaloriesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CaloriesApiStatus.ERROR
                _infoItem.value = listOf()
            }
        }
    }


    fun favItem(userId: String, itemLabel: String?) {
        //get Full Info from the item list {ITEM} var item=
        val respi= _infoItem.value?.find { it!!.recipe!!.label == itemLabel }
        respi?.let {

            val caloriesData: CaloriesData = CaloriesData(it.recipe?.image, it.recipe?.label,it.recipe?.getCaloriesAsString()?.toDouble(), it.recipe?.source, userId, it.recipe?.ingredientLines)


            //edit fave list on firestore
            addtoFirebase(caloriesData)
            //CaloriesDataCollection.document("users").collection(userId).add(favList!!)
            retriveData()
        }
    }


    fun unFavItem(userId: String, itemLabel: String?) {
        //get Full Info from the item list {ITEM} var item=
        val respi= _infoItem.value?.find { it!!.recipe!!.label == itemLabel }
        respi?.let {

            val caloriesData: CaloriesData = CaloriesData(it.recipe?.image, it.recipe?.label,it.recipe?.getCaloriesAsString()?.toDouble(), it.recipe?.source, userId, it.recipe?.ingredientLines)


            //edit fave list on firestore
            removeData(caloriesData)
            //CaloriesDataCollection.document("users").collection(userId).add(favList!!)
            retriveData()
        }


        //edit fave list on firestore upload list


    }



    fun isFav(userId: String, itemLabel: String?): Boolean {

      //  val one = async { one() }.await()
        //retriveData()
       viewModelScope.launch {
           retriveData()
       }



        Log.e("TAG", "isFav: ${_likeItem.value}", )
        var isFave = _likeItem.value?.find {


            if(  it?.label == itemLabel){
                Log.e("TAG", "isFav:  $itemLabel :: ${it?.label}", )
            }

            it?.label == itemLabel

        }
        Log.d("TAG", "favoriteCheck: $isFave ")
        isFave?.let {
            return true
        }
        return false


    }




    fun addtoFirebase(itemFavorate: CaloriesData) {
        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        CaloriesDataCollection.document("users").collection(userId).add(itemFavorate)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // val document = task.result
//                Toast.makeText(this.requireContext(), "Added to fov", Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun retriveData() {
        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        CaloriesDataCollection.document("users").collection(userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val item = mutableListOf<CaloriesData?>()
                    for (data in task.result!!.documents) {
                        val calories = data.toObject<CaloriesData>()
                        item.add(calories!!)
                    }
                    Log.d("TAG", "retriveData: $item")
                    _likeItem.value = item
//                binding.favoriteItem = item
                } else {

                }
            }.addOnFailureListener {
            println(it.message)
        }
    }


    fun fillFavorite(isFav: Boolean): Boolean {
        var isFavorate = _likeItem.value?.find { it?.isFav == isFav }
        isFavorate?.let {
            return true
        }
        return false
    }


    fun removeData(itemFavorate: CaloriesData) {
        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        CaloriesDataCollection.document("users").collection(userId)
            .whereEqualTo("image", itemFavorate.image)
            .whereEqualTo("label", itemFavorate.label)
            .whereEqualTo("calories", itemFavorate.calories)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.documents.isNotEmpty()) {
                        for (data in task.result!!.documents) {
                            CaloriesDataCollection.document("users").collection(userId)
                                .document(data.id).delete()
                            retriveData()
                        }
                    } else {

                    }
                }
            }
    }


}