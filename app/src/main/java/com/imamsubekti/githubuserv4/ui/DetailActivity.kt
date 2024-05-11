package com.imamsubekti.githubuserv4.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.imamsubekti.githubuserv4.R
import com.imamsubekti.githubuserv4.databinding.ActivityDetailBinding
import com.imamsubekti.githubuserv4.model.DetailViewModel
import com.imamsubekti.githubuserv4.ui.adapter.SectionPagerAdaptor

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val USER_PROFILE = "user profile"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USER_PROFILE) as String

        loadDetail(username)
        setupTabSection(username)
    }

    private fun loadDetail(username: String){
        if (username != detailViewModel.username) {
            showLoading()
            detailViewModel.username = username
        }

        detailViewModel.userDetail.observe(this) { response ->
            binding.userName.text = response.login
            binding.realName.text = response.name
            binding.followers.text = response.followers.toString()
            binding.following.text = response.following.toString()
            Glide.with(this).load(response.avatarUrl).circleCrop().into(binding.userImage)
            binding.buttonToGithub.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.htmlUrl))
                startActivity(intent)
            }
            showLoading(false)
        }
    }

    private fun setupTabSection(username: String){
        val tabs: TabLayout = binding.tabs
        val viewPager: ViewPager2 = binding.viewPager

    }

    private fun showLoading(isLoading: Boolean = true){
        if (isLoading) {
            binding.loadingLinearLayout.visibility = View.VISIBLE
            binding.viewPager.visibility = View.GONE
            binding.mainLinearLayout.visibility = View.GONE
        } else {
            binding.loadingLinearLayout.visibility = View.GONE
            binding.viewPager.visibility = View.VISIBLE
            binding.mainLinearLayout.visibility = View.VISIBLE

        }
    }
}