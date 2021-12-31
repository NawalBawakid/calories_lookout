package com.calories.calorieslookout.database

import com.squareup.moshi.Json
import kotlin.math.roundToInt

data class CaloriesData (

        @Json(name="image")
        val image: String? = null,

        @Json(name="label")
        val label: String? = null,

        @Json(name="calories1323")
         val calories: Double? = null,

        @Json(name="source")
        val source: String? = null,

        @Json(name="Key")
        val Key: String? = null

    )


