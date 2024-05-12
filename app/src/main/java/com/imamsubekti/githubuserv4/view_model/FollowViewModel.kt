package com.imamsubekti.githubuserv4.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imamsubekti.githubuserv4.entity.UserResponse
import com.imamsubekti.githubuserv4.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class FollowViewModel: ViewModel() {
    protected abstract fun setService(username: String): Call<List<UserResponse>>

    protected val apiService = ApiConfig.getApiService()

    private val _listUser = MutableLiveData<List<UserResponse>>()
    val listUser: LiveData<List<UserResponse>> = _listUser

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _searchKeyword = MutableLiveData<String>()
    var searchKeyword: String
        get() = _searchKeyword.value ?: "imamsubekti"
        set(value) {
            _searchKeyword.value = value
            updateList(value)
        }

    private fun updateList(username: String){
        setService(username).enqueue(object : Callback<List<UserResponse>>{
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    _listUser.postValue(response.body())
                    _error.value = false
                    Log.e("MainViewModel", "onResponseSuccess" )
                } else {
                    _error.value = true
                    Log.e("MainViewModel", "onResponseFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _error.value = true
                Log.e("MainViewModel", "onResponseFailure: ${t.message}")
            }
        })
    }
}