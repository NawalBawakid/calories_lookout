package com.calories.calorieslookout.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.calories.calorieslookout.database.CaloriesData
import com.calories.calorieslookout.network.BreakfastApi
import com.calories.calorieslookout.network.BreakfastApiService
import com.calories.calorieslookout.network.HitsItem
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.logging.Logger

enum class CaloriesApiStatus{LOADING, ERROR, DONE}
class OverviewViewModel : ViewModel(){

    private val _status = MutableLiveData<CaloriesApiStatus>()
    val status: LiveData<CaloriesApiStatus> = _status

    private val _infoItem = MutableLiveData<List<HitsItem?>?>()
    var infoItem: LiveData<List<HitsItem?>?> = _infoItem

    private val _photos = MutableLiveData<String>()
    val photos: LiveData<String> = _photos

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _descriptions = MutableLiveData<String>()
    val descriptions: LiveData<String> = _descriptions

    private val _calories = MutableLiveData<String>()
    val calories: LiveData<String> = _calories

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
        _calories.value = item?.recipe?.getCalories()
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


    fun favorite(index: Int) : CaloriesData{
        var item = _infoItem.value?.get(index)

        _photos.value = item?.recipe?.image
        _title.value = item?.recipe?.label
        _calories.value = item?.recipe?.getCalories()

        return CaloriesData(_photos.value,_title.value ,_calories.value?.toDouble())
    }

//    fun addFavorite(favoritList: CaloriesData){
//        favoritList.add()
//    }


//    fun addTask(taskModel: CaloriesData) {
//        taskModel.add(taskModel)
//    }

}
