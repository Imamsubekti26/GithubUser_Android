package com.imamsubekti.githubuserv4.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imamsubekti.githubuserv4.entity.DetailResponse
import com.imamsubekti.githubuserv4.entity.FavoriteUser
import com.imamsubekti.githubuserv4.network.ApiConfig
import com.imamsubekti.githubuserv4.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val favoriteRepository = FavoriteRepository(application)

    private val _username = MutableLiveData<String>()
    var username: String
        get() = _username.value ?: "imamsubekti"
        set(value) {
            _username.value = value
            updateUserDetail(value)
        }

    private fun updateUserDetail(username: String){
        apiService.getDetail(username).enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _userDetail.postValue(response.body())
                    _error.value = false
                } else {
                    _error.value = true
                    Log.e("DetailViewModel", "onResponseFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _error.value = true
                Log.e("MainViewModel", "onResponseFailure: ${t.message}")
            }
        })
    }

    fun countByUsername(username: String) = favoriteRepository.countByUsername(username)

    fun addToFavorite(user: FavoriteUser) = favoriteRepository.insert(user)

    fun removeFromFavorite(user: FavoriteUser)= favoriteRepository.delete(user)

}