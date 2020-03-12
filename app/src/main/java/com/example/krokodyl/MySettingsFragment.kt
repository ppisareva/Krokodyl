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
        (activity as AppCompatActivity).toolbar?.title = getString(R.string.settings)

        timerPreference?.setOnPreferenceChangeListener({pref, newValue ->
            pref.summary = "таймер встановлено на ${transferTime(newValue.toString())}"
            true
        })



    }

    private fun transferTime(value : String): String {
       val arr =  resources.getStringArray(R.array.timer_value)
        val arrVal =  resources.getStringArray(R.array.timer_name)
        return arrVal.get(arr.indexOf(value))

    }


}