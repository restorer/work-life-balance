package com.eightsines.worklifebalance.core.manager

import android.content.SharedPreferences
import com.eightsines.worklifebalance.core.di.qualifiers.IsDefaultSharedPreferences
import com.eightsines.worklifebalance.core.entity.Goal
import com.eightsines.worklifebalance.core.util.ext.fromJsonExt
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
        @IsDefaultSharedPreferences private val sharedPreferences : SharedPreferences,
        private val gson : Gson) {

    companion object {
        private const val KEY_GOALS = "KEY_GOALS"
    }

    fun hasGoals() : Boolean = contains(KEY_GOALS)
    // fun getGoals() : List<Goal> = gson.fromJsonExt(getString(KEY_GOALS))!!

    fun getGoals() : List<Goal> {
        val str = getString(KEY_GOALS).replace(Regex("\"desiredTime\":(\\d+)"), "\"desiredTimeList\":[$1]")
                .replace(Regex("\"totalTime\":(\\d+)"), "\"totalTimeList\":[$1]")

        return gson.fromJsonExt(str)!!
    }

    fun putGoals(value : List<Goal>) {
        putString(KEY_GOALS, gson.toJson(value))
    }

    private fun contains(key : String) = sharedPreferences.contains(key)

    private fun remove(key : String) {
        sharedPreferences.edit().remove(key).apply()
    }

    private fun getString(key : String) : String = getString(key, "")
    private fun getString(key : String, def : String) : String = sharedPreferences.getString(key, def)

    private fun putString(key : String, value : String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getBoolean(key : String) : Boolean = getBoolean(key, false)
    private fun getBoolean(key : String, def : Boolean) : Boolean = sharedPreferences.getBoolean(key, def)

    private fun putBoolean(key : String, value : Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private fun getLong(key : String) : Long = getLong(key, 0L)
    private fun getLong(key : String, def : Long) : Long = sharedPreferences.getLong(key, def)

    private fun putLong(key : String, value : Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    private fun getStringSet(key : String) : Set<String> = getStringSet(key, HashSet<String>())!!

    private fun getStringSet(key : String, def : Set<String>) : Set<String>? {
        val result = sharedPreferences.getStringSet(key, def)
        // from the docs: "Note that you _must not_ modify the set instance returned by this call"
        return if (result == null) null else HashSet(result)
    }

    private fun putStringSet(key : String, value : Set<String>?) {
        sharedPreferences.edit()
                .putStringSet(key, if (value == null) null else HashSet(value))
                .apply()
    }
}
