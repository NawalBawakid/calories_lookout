package com.calories.calorieslookout.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



private const val BASE_URL = "https://api.edamam.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BreakfastApiService {
//    @GET("/search?q={type}&app_id=fafcd302&app_key=76b9f87704af9178637d1b74796f09c6")
//    suspend fun getItem(@Path("type") mealType: String): BreakfastResponse


    @GET("/search?q=breakfast&app_id=fafcd302&app_key=76b9f87704af9178637d1b74796f09c6")
    suspend fun getPhotos(@Query("q") type: String): BreakfastResponse
}


object BreakfastApi {
    val retrofitService : BreakfastApiService by lazy { retrofit.create(BreakfastApiService::class.java) }
}

