package com.imamsubekti.githubuserv4.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imamsubekti.githubuserv4.entity.SearchResponse
import com.imamsubekti.githubuserv4.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _listUser = MutableLiveData<SearchResponse>()
    val listUser: LiveData<SearchResponse> = _listUser

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
        apiService.getSearch(username).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
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

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _error.value = true
                Log.e("MainViewModel", "onResponseFailure: ${t.message}")
            }
        })
    }
}