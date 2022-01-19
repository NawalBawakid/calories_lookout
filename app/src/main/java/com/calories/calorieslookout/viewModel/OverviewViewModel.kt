package com.calories.calorieslookout.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.database.ProfileImage
import com.calories.calorieslookout.network.BreakfastApi
import com.calories.calorieslookout.network.HitsItem
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.*
import java.util.*


enum class CaloriesApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<CaloriesApiStatus>()
    var status: LiveData<CaloriesApiStatus> = _status

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

    private val _ingredient = MutableLiveData<String>()
    val ingredient: LiveData<String> = _ingredient

    private val CaloriesDataCollection = Firebase.firestore.collection("CaloriesData")
    private val ImageCollection = Firebase.firestore.collection("posts")

    private val _caloriesNum = MutableLiveData<Double>()
    val caloriesNum: LiveData<Double> = _caloriesNum

    var userNames = FirebaseAuth.getInstance().currentUser?.displayName

    var userEmail = FirebaseAuth.getInstance().currentUser?.email

    private val _foodId = MutableLiveData<String>()
    val foodId: LiveData<String> = _foodId

    init {
        getMealsPhotos("breakfast")
        retriveData()
    }


    fun getMealsPhotos(type: String) {
        viewModelScope.launch {
            _status.value = CaloriesApiStatus.LOADING
            try {
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

    // Calories Calcolator for male
    fun maleCalories(weights: Double, heights: Int, ages: Int): Double {
        return ((10 * weights) + (6.25 * heights) - (5 * ages) + 5)
    }

    // Calories Calcolator for female
    fun femaleCalories(weights: Double, heights: Int, ages: Int): Double {
        return ((10 * weights) + (6.25 * heights) - (5 * ages) - 161)
    }

    // retrive profile image from firestore
    fun retriveImages() {
        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        var img =  ImageCollection.document(userId).get()

        img.addOnCompleteListener { task ->
                _status.value = CaloriesApiStatus.LOADING
                if (task.isSuccessful) {
                    _profileImage.value = task.result!!.data?.get("imageUrl").toString()

                } else {

                }
                _status.value = CaloriesApiStatus.DONE
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

        val respi= _infoItem.value?.find { it!!.recipe!!.label == itemLabel }
        respi?.let {
            val caloriesData: CaloriesData = CaloriesData(it.recipe?.image, it.recipe?.label,it.recipe?.getCaloriesAsString()?.toDouble(), it.recipe?.source, userId, it.recipe?.ingredientLines)

            addtoFirebase(caloriesData)
            retriveData()
        }
    }


    fun unFavItem(userId: String, itemLabel: String?) {
        val respi= _infoItem.value?.find { it!!.recipe!!.label == itemLabel }
        respi?.let {

            val caloriesData: CaloriesData = CaloriesData(it.recipe?.image, it.recipe?.label,it.recipe?.getCaloriesAsString()?.toDouble(), it.recipe?.source, userId, it.recipe?.ingredientLines)

            removeData(caloriesData)
            retriveData()
        }
    }



    fun isFav(userId: String, itemLabel: String?): Boolean {
        viewModelScope.launch {
           retriveData()
        }

        var isFave = _likeItem.value?.find {

            if(  it?.label == itemLabel){
                Log.e("TAG", "isFav:  $itemLabel :: ${it?.label}", )
            }

            it?.label == itemLabel
        }

        isFave?.let {
            return true
        }
        return false
    }


    // add favorit data to firestore
    fun addtoFirebase(itemFavorate: CaloriesData) {
        var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        CaloriesDataCollection.document("users").collection(userId).add(itemFavorate)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                }
            }
    }

    // retrive data from firestore
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
                    _likeItem.value = item
                } else {

                }
            }.addOnFailureListener {
            println(it.message)
        }
    }

    // remove favorite data from firestore
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