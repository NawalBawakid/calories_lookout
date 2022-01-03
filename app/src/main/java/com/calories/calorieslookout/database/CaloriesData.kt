package com.calories.calorieslookout.database

import com.squareup.moshi.Json
import kotlin.math.roundToInt

data class CaloriesData (

        @Json(name="image")
        val image: String? = null,

        @Json(name="label")
        val label: String? = null,

        @Json(name="calories")
         val calories: Double? = null,

        @Json(name="source")
        val source: String? = null,

        @Json(name="Key")
        val Key: String? = null,

        @Json(name="ingredientLines")
        val ingredientLines: List<String?>? = null,

        @Json(name="isFav")
        val isFav:Boolean = false
    )


