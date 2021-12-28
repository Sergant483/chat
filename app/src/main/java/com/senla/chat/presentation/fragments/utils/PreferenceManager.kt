package com.senla.chat.presentation.fragments.utils

import android.content.Context

class PreferenceManager(context: Context) {

    private var sharedPreferences =
        context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private var preferencesEditor = sharedPreferences.edit()


    fun putBoolean(key: String, value: Boolean) {
        preferencesEditor.putBoolean(key, value)
        preferencesEditor.apply()

    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)

    }

    fun putString(key: String, value: String) {
        preferencesEditor.putString(key, value)
        preferencesEditor.apply()

    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }

    fun clear() {
        preferencesEditor.clear()
        preferencesEditor.apply()

    }

    companion object {
        const val KEY_PREFERENCE_NAME = "chatAppPreference"
    }
}