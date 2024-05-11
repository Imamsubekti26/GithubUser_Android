package com.imamsubekti.githubuserv4.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.imamsubekti.githubuserv4.entity.FavoriteUser
import com.imamsubekti.githubuserv4.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val favoriteRepository = FavoriteRepository(application)

    fun getAll(): LiveData<List<FavoriteUser>> {
        return favoriteRepository.getAll()
    }
}