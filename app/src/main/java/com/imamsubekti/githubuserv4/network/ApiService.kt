package com.imamsubekti.githubuserv4.network

import com.imamsubekti.githubusersv2.entity.DetailResponse
import com.imamsubekti.githubusersv2.entity.UserResponse
import com.imamsubekti.githubusersv2.entity.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearch(@Query("q") searchKey: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserResponse>>

}