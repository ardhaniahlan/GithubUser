package org.apps.githubuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.apps.githubuser.ui.setting.SetViewModel
import org.apps.githubuser.ui.setting.SettingPreferences

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetViewModel::class.java)) {
            return SetViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}