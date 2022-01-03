package com.calories.calorieslookout.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.network.BreakfastApi
import com.calories.calorieslookout.network.BreakfastApiService
import com.calories.calorieslookout.network.HitsItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.StringBuilder
import java.util.logging.Logger
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth


enum class CaloriesApiStatus{LOADING, ERROR, DONE}
class OverviewViewModel : ViewModel(){

    private val _status = MutableLiveData<CaloriesApiStatus>()
    val status: LiveData<CaloriesApiStatus> = _status

    private val _infoItem = MutableLiveData<List<HitsItem?>?>()
    var infoItem: LiveData<List<HitsItem?>?> = _infoItem

    private val _likeItem = MutableLiveData<List<CaloriesData?>?>()
    var likeItem: LiveData<List<CaloriesData?>?> = _likeItem

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

    private val _caloriesNum = MutableLiveData<Double>()
    val caloriesNum: LiveData<Double> = _caloriesNum

    private val _foodId = MutableLiveData<String>()
    val foodId: LiveData<String> = _foodId


//    private val _isFavorite = MutableLiveData<Boolean>()
//    val isFavorite: LiveData<Boolean> = _isFavorite


//    private val apiService = BreakfastApiService()
    private val mutableSearchTerm = MutableLiveData<String>()

    val favoritList: MutableList<CaloriesData> = mutableListOf()



    init {
        getMealsPhotos("breakfast")
    }


     fun getMealsPhotos(type: String) {
        viewModelScope.launch {
            _status.value = CaloriesApiStatus.LOADING
            try {
                Log.e("try", "${ _infoItem.value}")
                when(type){
                    "breakfast" -> {
                        _infoItem.value = BreakfastApi.retrofitService.getPhotos("breakfast").hits
                        Log.e("br", "${ _infoItem.value}")
                    }
                    "lunch" -> _infoItem.value = BreakfastApi.retrofitService.getPhotos("lunch").hits
                    "dinner" -> _infoItem.value = BreakfastApi.retrofitService.getPhotos("dinner").hits
                    else -> _infoItem.value = BreakfastApi.retrofitService.getPhotos("breakfast").hits
                }
                _status.value = CaloriesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CaloriesApiStatus.ERROR
                _infoItem.value = listOf()
            }
        }
    }

    fun informationll(index: Int) {
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
                when(query){
                    query -> _infoItem.value = BreakfastApi.retrofitService.getPhotos(query).hits
                    else -> _infoItem.value = BreakfastApi.retrofitService.getPhotos("breakfast").hits
                }
                _status.value = CaloriesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CaloriesApiStatus.ERROR
                _infoItem.value = listOf()
            }
        }
    }


    fun favorite(index: Int, userID: String) : CaloriesData{
        var item = _infoItem.value?.get(index)

        _photos.value = item?.recipe?.image
        _title.value = item?.recipe?.label
        _calories.value = item?.recipe?.getCaloriesAsString()
        _foodId.value = item?.recipe?.source

        return CaloriesData(item?.recipe?.image,item?.recipe?.label,item?.recipe?.getCalories(), item?.recipe?.source, userID, item?.recipe?.ingredientLines)
    }


    fun addtoFirebase(itemFavorate : CaloriesData){
        var userId=FirebaseAuth.getInstance().currentUser?.uid?:""
        CaloriesDataCollection.document("users").collection(userId).add(itemFavorate)
            .addOnCompleteListener{task ->
            if (task.isSuccessful){
               // val document = task.result
//                Toast.makeText(this.requireContext(), "Added to fov", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun retriveData(){
        var userId=FirebaseAuth.getInstance().currentUser?.uid?:""
        CaloriesDataCollection.document("users").collection(userId).get().addOnCompleteListener{task ->
            if (task.isSuccessful) {
                val item = mutableListOf<CaloriesData>()
                for (data in task.result!!.documents) {
                    val calories = data.toObject<CaloriesData>()
                    item.add(calories!!)
                }
                Log.d("TAG", "retriveData: $item")
                _likeItem.value=item
//                binding.favoriteItem = item
            }else{

            }
        }.addOnFailureListener {
            println(it.message)
        }
    }


    fun isFavorit(label: String):Boolean{
       var isFave= _likeItem.value?.find { it?.label == label }
        Log.d("TAG", "favoriteCheck: $isFave ")
        isFave?.let {
         return true
        }
        return false
    }


    fun removeData(itemFavorate : CaloriesData){
        var userId=FirebaseAuth.getInstance().currentUser?.uid?:""
        CaloriesDataCollection.document("users").collection(userId).whereEqualTo("image", itemFavorate.image)
            .whereEqualTo("label", itemFavorate.label)
            .whereEqualTo("calories", itemFavorate.calories)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    if (task.result!!.documents.isNotEmpty()){
                        for (data in task.result!!.documents){
                            CaloriesDataCollection.document("users").collection(userId).document(data.id).delete()
                            retriveData()
                        }
                    }else{

                    }
                }

            }

    }


//    for (data in task.result!!.documents) {
//        val calories = data.toObject<CaloriesData>()
//        item.document(data.id).delete(calories!!)
//    }

}
