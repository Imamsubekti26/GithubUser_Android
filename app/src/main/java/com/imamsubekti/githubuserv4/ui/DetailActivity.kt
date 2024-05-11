package com.imamsubekti.githubuserv4.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.imamsubekti.githubuserv4.R
import com.imamsubekti.githubuserv4.databinding.ActivityDetailBinding
import com.imamsubekti.githubuserv4.entity.FavoriteUser
import com.imamsubekti.githubuserv4.view_model.DetailViewModel
import com.imamsubekti.githubuserv4.ui.adapter.SectionPagerAdaptor
import com.imamsubekti.githubuserv4.view_model.factory.FavoriteViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var userParcel: FavoriteUser? = null
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

        userParcel = FavoriteUser()

        val factory = FavoriteViewModelFactory.getInstance(this.application)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        loadDetail(username)
        setupFavorite(username)
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
            userParcel?.id = response.id
            userParcel?.username = response.login
            userParcel?.picture = response.avatarUrl
            showLoading(false)
        }
    }

    private fun setupFavorite(username: String){
        detailViewModel.isFavorite.observe(this) {
            Log.i("DetailActivity", "loadDetail: favorite is $it")
            val buttonFav = binding.buttonAddToFavorite
            if (it) {
                buttonFav.setIconResource(R.drawable.ic_heart_block)
                buttonFav.setOnClickListener {
                    detailViewModel.removeFromFavorite(userParcel as FavoriteUser)
                }
            } else {
                buttonFav.setIconResource(R.drawable.ic_heart_outline)
                buttonFav.setOnClickListener {
                    detailViewModel.addToFavorite(userParcel as FavoriteUser)
                }
            }
        }
    }

    private fun setupTabSection(username: String){
        val tabs: TabLayout = binding.tabs
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = SectionPagerAdaptor(this, username)

        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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