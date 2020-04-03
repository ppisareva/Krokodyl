package com.example.krokodyl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_main.*

class MySettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val timerPreference: ListPreference? = findPreference("timer_settings")
        val teamPreference: ListPreference?=findPreference("team_settings")
        (activity as AppCompatActivity).toolbar?.title = getString(R.string.settings)
        timerPreference!!.summary = "${getString(R.string.timer_summery)} ${transferTime(AppPreferences.timer.toString())}"
        teamPreference!!.summary = "${transferTeam(AppPreferences.team.toString())}"


        timerPreference.setOnPreferenceChangeListener({pref, newValue ->
            pref.summary = "${getString(R.string.timer_summery)} ${transferTime(newValue.toString())}"
            AppPreferences.timer = newValue.toString().toLong()
            true
        })

        teamPreference?.setOnPreferenceChangeListener({ pref, newValue ->
            pref.summary = "${transferTeam(newValue.toString())}"
            AppPreferences.team = newValue.toString().toInt()
            true
        })

    }

    private fun transferTime(value : String): String {
       val arr =  resources.getStringArray(R.array.timer_value)
        val arrVal =  resources.getStringArray(R.array.timer_name)
        return arrVal.get(arr.indexOf(value))
    }

    private fun transferTeam(value : String):String{
        val arr =  resources.getStringArray(R.array.team_value)
        val arrVal =  resources.getStringArray(R.array.team_name)
        return arrVal.get(arr.indexOf(value))
    }


}