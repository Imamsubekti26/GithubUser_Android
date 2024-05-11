package com.imamsubekti.githubuserv4.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imamsubekti.githubuserv4.repository.ThemePreference
import com.imamsubekti.githubuserv4.view_model.DarkModeViewModel

class ThemeViewModelFactory(private val pref: ThemePreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)) {
            return DarkModeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}