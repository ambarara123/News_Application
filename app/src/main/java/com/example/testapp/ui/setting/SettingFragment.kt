package com.example.testapp.ui.setting

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.testapp.R
import com.example.testapp.utils.PREF_NAME
import timber.log.Timber

class SettingFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_setting)

        bindPreference(findPreference(getString(R.string.key_mode))!!)
    }

    private fun bindPreference(preference: Preference) {
        preference.onPreferenceChangeListener = this

        onPreferenceChange(
            preference, preference.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .getBoolean(preference.key, false)
        )
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val value = newValue.toString().toBoolean()
        preference?.context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)!!
            .edit().putBoolean(preference.key, value)
            .apply()

        if (value) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        Timber.d(value.toString())
        return true
    }

}