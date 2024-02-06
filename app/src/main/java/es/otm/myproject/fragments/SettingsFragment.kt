package es.otm.myproject.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.otm.myproject.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}