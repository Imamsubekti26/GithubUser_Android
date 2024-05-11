package com.imamsubekti.githubuserv4.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imamsubekti.githubusersv2.entity.DetailResponse
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

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _username = MutableLiveData<String>()
    var username: String
        get() = _username.value ?: "imamsubekti"
        set(value) {
            _username.value = value
            updateUserDetail(value)
            favoriteCheck(value)
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
                    Log.e("MainViewModel", "onResponseSuccess" )
                } else {
                    _error.value = true
                    Log.e("MainViewModel", "onResponseFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _error.value = true
                Log.e("MainViewModel", "onResponseFailure: ${t.message}")
            }
        })
    }

    private fun favoriteCheck(username: String) {
//        val dataSize = favoriteRepository.show(username).value
        val dataSize = 0
        Log.i("DetailViewModel", "favoriteCheck: is running with value $dataSize")
        if (dataSize != null) {
            _isFavorite.postValue( dataSize > 0)
        } else {
            _isFavorite.postValue(false)
        }
    }

    fun addToFavorite(user: FavoriteUser) {
        favoriteRepository.insert(user)
        _isFavorite.postValue(true)
    }

    fun removeFromFavorite(user: FavoriteUser) {
        favoriteRepository.delete(user)
        _isFavorite.postValue(false)
    }
}