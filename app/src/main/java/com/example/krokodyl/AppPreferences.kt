package com.example.krokodyl

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "krokodyl"
    private const val MODE = Context.MODE_PRIVATE
    private const val FIRST_RUN = "is_first_run"
    private const val TIMER = "timer"
    private const val TEAM ="team"

    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair(FIRST_RUN, false)
    private val TIMER_PREF = Pair(TIMER, 60000L)
    private val TEAM_PREF = Pair(TEAM, 1)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var timer : Long
    get() = preferences.getLong(TIMER_PREF.first, TIMER_PREF.second)
    set(value) = preferences.edit{
        it.putLong(TIMER_PREF.first, value)
    }

    var team : Int
        get() = preferences.getInt(TEAM_PREF.first, TEAM_PREF.second)
        set(value) = preferences.edit{
            it.putInt(TEAM_PREF.first, value)
        }

    var firstRun: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }
}