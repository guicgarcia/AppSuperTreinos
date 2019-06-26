package com.example.t_gamer.supertreinos.db.dao

import androidx.room.*
import com.example.t_gamer.supertreinos.entities.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAll(): Single<List<User>>

    @Insert
    fun insert(user: User): Single<Long>

    @Update
    fun update(user: User): Completable

    @Delete
    fun remove(user: User): Completable
}