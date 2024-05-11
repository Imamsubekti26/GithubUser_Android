package com.imamsubekti.githubuserv4.model

import com.imamsubekti.githubusersv2.entity.UserResponse
import retrofit2.Call

class FollowerViewModel: FollowViewModel() {
    override fun setService(username: String): Call<List<UserResponse>> {
        return apiService.getFollowers(username)
    }

}