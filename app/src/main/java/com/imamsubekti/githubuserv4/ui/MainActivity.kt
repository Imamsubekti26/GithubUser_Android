package com.imamsubekti.githubuserv4.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.imamsubekti.githubuserv4.R
import com.imamsubekti.githubuserv4.repository.ThemePreference
import com.imamsubekti.githubuserv4.view_model.factory.ThemeViewModelFactory
import com.imamsubekti.githubuserv4.repository.dataStore
import com.imamsubekti.githubuserv4.databinding.ActivityMainBinding
import com.imamsubekti.githubuserv4.view_model.DarkModeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var darkModeViewModel: DarkModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDarkMode()
        setupSearchBar()
        setupSearchView()
    }

    private fun setupDarkMode() {
        val pref = ThemePreference.getInstance(application.dataStore)
        darkModeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[DarkModeViewModel::class.java]
        darkModeViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupSearchBar() {
        binding.searchBar.inflateMenu(R.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.dark_mode_toggle -> {
                    var toggleDarkModeClicked = true
                    darkModeViewModel.getThemeSettings().observe(this) { isDarkModeOn ->
                        if (toggleDarkModeClicked) {
                            darkModeViewModel.saveThemeSetting(!isDarkModeOn)
                            toggleDarkModeClicked = false
                        }
                    }
                    true
                }
                R.id.favorite_users -> {
                    val toDetail = Intent(this, FavoriteActivity::class.java)
                    startActivity(toDetail)
                    true
                }
                else -> false
            }
        }

        binding.searchView.setupWithSearchBar(binding.searchBar)
    }

    private fun loadUserList(searchKey: String){
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(UserListFragment::class.java.simpleName)

        val listFragment = UserListFragment().apply {
            arguments = Bundle().apply {
                putInt("position", 2)
                putString("username", searchKey)
            }
        }

        fragmentManager.beginTransaction().apply {
            if (existingFragment == null) {
                add(binding.rvUser.id, listFragment, UserListFragment::class.java.simpleName)
            } else {
                replace(binding.rvUser.id, listFragment, UserListFragment::class.java.simpleName)
            }

            commit()
        }
    }

    private fun setupSearchView(){
        binding.searchView.editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.searchBar.setText(binding.searchView.text)
                binding.searchView.hide()
                loadUserList(binding.searchView.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}