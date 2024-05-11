package com.imamsubekti.githubuserv4.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imamsubekti.githubusersv2.entity.UserResponse
import com.imamsubekti.githubuserv4.databinding.UserCardBinding
import com.imamsubekti.githubuserv4.ui.DetailActivity

class UserListAdapter(private val dataList: List<UserResponse>) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(holder.itemView.context, dataList[position])
    }

    class UserViewHolder (private val binding: UserCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.userCard.setOnClickListener {
                val context = binding.userCard.context
                val toDetail = Intent(context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.USER_PROFILE, binding.userName.text.toString())
                }
                context.startActivity(toDetail)
            }
        }

        fun bind(context: Context, user: UserResponse) {
            binding.userName.text = user.login
            Glide.with(context).load(user.avatarUrl).circleCrop().into(binding.userImage)
        }
    }
}