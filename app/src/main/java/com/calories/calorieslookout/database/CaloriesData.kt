package com.calories.calorieslookout.database

import com.squareup.moshi.Json
import kotlin.math.roundToInt

data class CaloriesData (

        @Json(name="image")
        val image: String? = null,

        @Json(name="label")
        val label: String? = null,

        @Json(name="calories")
        private val calories: Double? = null

    ){
        fun getCalories():String = calories?.roundToInt()?.toString()?:""
    }


//class TaskModel(var checked: Boolean, var task: String, var dueDate: String, var description: String) {
//}