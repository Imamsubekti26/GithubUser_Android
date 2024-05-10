package com.imamsubekti.githubuserv4.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imamsubekti.githubusersv2.entity.SearchResponse
import com.imamsubekti.githubusersv2.entity.UserResponse
import com.imamsubekti.githubuserv4.R
import com.imamsubekti.githubuserv4.databinding.UserCardBinding

class UserListAdapter(private val dataList: SearchResponse) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = dataList.items
        holder.bind(holder.itemView.context, users[position])
    }

    class UserViewHolder (private val binding: UserCardBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(context: Context, user: UserResponse) {
            binding.userName.text = user.login
            Glide.with(context).load(user.avatarUrl).circleCrop().into(binding.userImage)
        }
    }
}