package com.dbeqiraj.pointofsale.ui.config

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.base.BaseActivity

import kotlinx.android.synthetic.main.activity_config.*

class ConfigActivity : BaseActivity() {

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        title = getString(R.string.configurations)
        showBackArrow()
        replaceBodyContent()
    }

    private fun replaceBodyContent() {
        fragmentManager.beginTransaction()
                .replace(R.id.settings_body, GeneralPreferenceFragment())
                .commit()
    }

    class GeneralPreferenceFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_config)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
            super.onPause()
        }

        override fun onSharedPreferenceChanged(prefs: SharedPreferences, key: String) {}
    }

    override fun getContentView(): Int = R.layout.activity_config
}
