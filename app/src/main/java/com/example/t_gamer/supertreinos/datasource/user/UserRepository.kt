package com.example.t_gamer.supertreinos.datasource.user

import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.app.user.UserApplication
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository: UserDataSource {

    val dataSource: UserDataSource
        get() = if (UserApplication.app.networkAvailable)
            UserRemoteDataSource()
        else
            UserLocalDataSource()

    override fun getAll() = dataSource
        .getAll()
        .subscribeOn(Schedulers.io())

    override fun insert(user: User) = dataSource
        .insert(user)
        .subscribeOn(Schedulers.io())

    override fun update(user: User): Completable = dataSource
        .update(user)
        .subscribeOn(Schedulers.io())

    override fun remove(user: User) = dataSource
        .remove(user)
        .subscribeOn(Schedulers.io())

    override fun login(username: String, password: String): Single<User> {
        return dataSource.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun find(id: Long): Single<User> {
        return dataSource.find(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
