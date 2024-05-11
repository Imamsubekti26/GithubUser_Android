package com.imamsubekti.githubuserv4.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imamsubekti.githubuserv4.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

    @Delete
    fun delete(user: FavoriteUser)

    @Query("SELECT * FROM favoriteuser ORDER BY id ASC")
    fun getAll(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favoriteuser WHERE username = :username")
    fun show(username: String): LiveData<List<FavoriteUser>>
}