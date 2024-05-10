package com.imamsubekti.githubuserv4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.imamsubekti.githubuserv4.R
import com.imamsubekti.githubuserv4.config.ThemePreference
import com.imamsubekti.githubuserv4.config.ViewModelFactory
import com.imamsubekti.githubuserv4.config.dataStore
import com.imamsubekti.githubuserv4.databinding.ActivityMainBinding
import com.imamsubekti.githubuserv4.model.DarkModeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var darkModeViewModel: DarkModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDarkModeConfig()
        initSearchBar()
    }

    private fun initDarkModeConfig() {
        val pref = ThemePreference.getInstance(application.dataStore)
        darkModeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[DarkModeViewModel::class.java]
        darkModeViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun initSearchBar() {
        binding.searchBar.inflateMenu(R.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dark_mode_toggle -> {
                    var toggleDarkModeClicked = true
                    darkModeViewModel.getThemeSettings().observe(this) {
                        if (toggleDarkModeClicked) {
                            darkModeViewModel.saveThemeSetting(!it)
                            toggleDarkModeClicked = false
                        }
                    }
                    true
                }
                R.id.favorite_users -> {
                    true
                }
                else -> false
            }
        }
    }

}