package com.example.t_gamer.supertreinos.datasource.user

import com.example.t_gamer.supertreinos.entities.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {

    fun getAll(): Single<List<User>>
    fun insert(user: User): Single<Long>
    fun update(user: User): Completable
    fun remove(user: User): Completable
    fun login(username: String, password: String): Single<User>
    fun find(id: Long): Single<User>
    
}