package com.imamsubekti.githubuserv4.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.imamsubekti.githubuserv4.R
import com.imamsubekti.githubuserv4.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        loadUserList()
    }

    private fun loadUserList(){
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(UserListFragment::class.java.simpleName)

        val listFragment = UserListFragment(this.application).apply {
            arguments = Bundle().apply {
                putInt("position", 3)
                putString("username", "imamsubekti")
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
}