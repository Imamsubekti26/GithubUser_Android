package com.imamsubekti.githubuserv4.view_model

import com.imamsubekti.githubuserv4.entity.UserResponse
import retrofit2.Call

class FollowerViewModel: FollowViewModel() {
    override fun setService(username: String): Call<List<UserResponse>> {
        return apiService.getFollowers(username)
    }

}