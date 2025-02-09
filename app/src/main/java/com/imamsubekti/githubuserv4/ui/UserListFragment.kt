package com.imamsubekti.githubuserv4.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.imamsubekti.githubuserv4.entity.UserResponse
import com.imamsubekti.githubuserv4.databinding.FragmentUserListBinding
import com.imamsubekti.githubuserv4.view_model.FollowerViewModel
import com.imamsubekti.githubuserv4.view_model.FollowingViewModel
import com.imamsubekti.githubuserv4.view_model.MainViewModel
import com.imamsubekti.githubuserv4.ui.adapter.UserListAdapter
import com.imamsubekti.githubuserv4.view_model.FavoriteViewModel
import com.imamsubekti.githubuserv4.view_model.factory.FavoriteViewModelFactory

class UserListFragment(private val application: Application? = null) : Fragment() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val followerViewModel by viewModels<FollowerViewModel>()
    private val followingViewModel by viewModels<FollowingViewModel>()
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position", 0)
        val username = arguments?.getString("username", "")

        when(position){
            0 -> getFollowers(username as String)
            1 -> getFollowing(username as String)
            2 -> findUsers(username as String)
            3 -> getFavoriteUsers()
        }

        val context = requireContext()
        val layoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun getFollowers(searchKey: String) {
        if (searchKey != followerViewModel.searchKeyword){
            showLoading()
            followerViewModel.searchKeyword = searchKey
        }

        followerViewModel.listUser.observe(viewLifecycleOwner) {
            binding.rvUser.adapter = UserListAdapter(it)
            showLoading(false)
        }

        followerViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                showErrorMsg()
            }
        }
    }

    private fun getFollowing(searchKey: String) {
        if (searchKey != followingViewModel.searchKeyword){
            showLoading()
            followingViewModel.searchKeyword = searchKey
        }

        followingViewModel.listUser.observe(viewLifecycleOwner) {
            binding.rvUser.adapter = UserListAdapter(it)
            showLoading(false)
        }

        followingViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                showErrorMsg()
            }
        }
    }

    private fun findUsers(searchKey: String) {
        if (searchKey != mainViewModel.searchKeyword){
            showLoading()
            mainViewModel.searchKeyword = searchKey
        }

        mainViewModel.listUser.observe(viewLifecycleOwner) {
            binding.rvUser.adapter = UserListAdapter(it.items)
            showLoading(false)
        }

        mainViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                showErrorMsg()
            }
        }
    }

    private fun getFavoriteUsers() {
        val factory = FavoriteViewModelFactory.getInstance(application as Application)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        favoriteViewModel.getAll().observe(viewLifecycleOwner) { response ->

            val users = response.map {
                UserResponse(login = it.username, avatarUrl = it.picture)
            }
            binding.rvUser.adapter = UserListAdapter(users)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean = true){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
            binding.errorMsg.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
            binding.errorMsg.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showErrorMsg() {
        showLoading(false)
        binding.rvUser.visibility = View.GONE
        binding.errorMsg.visibility = View.VISIBLE
        binding.errorMsg.text = "Ada error cuy, coba lagi taun depan"
    }
}