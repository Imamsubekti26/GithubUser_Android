package com.imamsubekti.githubuserv4.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imamsubekti.githubuserv4.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoom: RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoom? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoom {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoom::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserRoom::class.java, "user_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoom
        }
    }
}