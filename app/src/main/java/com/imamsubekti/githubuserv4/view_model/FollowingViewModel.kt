package com.imamsubekti.githubuserv4.view_model

import com.imamsubekti.githubusersv2.entity.UserResponse
import retrofit2.Call

class FollowingViewModel: FollowViewModel() {
    override fun setService(username: String): Call<List<UserResponse>> {
        return apiService.getFollowing(username)
    }

}