package com.example.t_gamer.supertreinos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.db.dao.UserDao

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    //abstract fun exerciseDao(): ExerciseDao
}