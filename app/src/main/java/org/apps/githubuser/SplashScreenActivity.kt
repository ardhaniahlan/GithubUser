package org.apps.githubuser

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import org.apps.githubuser.ui.setting.SetViewModel
import org.apps.githubuser.ui.setting.SettingPreferences
import org.apps.githubuser.ui.user.UserActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val setViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SetViewModel::class.java]

        setViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val delayMillis = 3000L

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, UserActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)

    }

}