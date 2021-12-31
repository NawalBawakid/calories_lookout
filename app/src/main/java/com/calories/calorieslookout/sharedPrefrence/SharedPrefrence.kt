package com.calories.calorieslookout.sharedPrefrence

import android.annotation.SuppressLint
import android.content.Context


const val GENERAL_PREF = "nurse_app_general_prefs"
const val PREFS_PRIVATE_MODE = 0

@SuppressLint("WrongConstant")
fun setPrefsString(context: Context, key: String, value: String) {
    context.getSharedPreferences(GENERAL_PREF, PREFS_PRIVATE_MODE).edit()
        .putString(key, value).apply()
}

@SuppressLint("WrongConstant")
fun getStringFromPrefs(context: Context, string: String): String? {
    val sharedPreferences = context.getSharedPreferences(GENERAL_PREF, PREFS_PRIVATE_MODE)
    return sharedPreferences.getString(string, "")
}