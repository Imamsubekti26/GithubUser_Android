package com.imamsubekti.githubuserv4.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.imamsubekti.githubuserv4.database.FavoriteUserDao
import com.imamsubekti.githubuserv4.database.FavoriteUserRoom
import com.imamsubekti.githubuserv4.entity.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoom.getDatabase(application)
        favoriteUserDao = db.favoriteUserDao()
    }

    fun getAll(): LiveData<List<FavoriteUser>> = favoriteUserDao.getAll()

    fun countByUsername(username: String): LiveData<List<FavoriteUser>> = favoriteUserDao.countByUsername(username)

    fun insert(user: FavoriteUser) {
        executorService.execute { favoriteUserDao.insert(user) }
    }

    fun delete(user: FavoriteUser) {
        executorService.execute { favoriteUserDao.delete(user) }
    }
}