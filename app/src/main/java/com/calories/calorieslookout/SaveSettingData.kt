package com.calories.calorieslookout

import android.content.Context
import android.content.SharedPreferences

class SaveSettingData(context: Context?) {
    private var sharedPreferences:SharedPreferences = context!!.getSharedPreferences("file", Context.MODE_PRIVATE)

    fun setDarkModeState(state: Boolean?){
        val editor = sharedPreferences.edit()
        editor.putBoolean("Dark", state!!)
        editor.apply()
    }

    fun loadDarkModeState():Boolean?{
        val state: Boolean = sharedPreferences.getBoolean("Dark", false)
        return (state)
    }















}