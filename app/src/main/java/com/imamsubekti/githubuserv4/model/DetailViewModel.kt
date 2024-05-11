package com.imamsubekti.githubuserv4.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imamsubekti.githubusersv2.entity.DetailResponse
import com.imamsubekti.githubuserv4.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

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
}