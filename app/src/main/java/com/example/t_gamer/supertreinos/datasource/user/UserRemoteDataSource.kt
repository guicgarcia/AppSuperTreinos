package com.example.t_gamer.supertreinos.datasource.user

import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.network.UserService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserRemoteDataSource: UserDataSource {

    var service: UserService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://guicgarcia.96.lt/treinos/admin/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create<UserService>(UserService::class.java)
    }

    override fun getAll(): Single<List<User>> {
        return service.getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun insert(user: User): Single<Long> {
        return service.createUser(user.name, user.email, user.username, user.password)
            .map { user.id }
            .subscribeOn(Schedulers.io())
    }

    override fun update(user: User): Completable {
        return service.updateUser(user.id, user.name, user.email, user.username, user.password)
            .subscribeOn(Schedulers.io())
    }

    override fun remove(user: User): Completable {
        return service.deleteUser(user.id)
            .subscribeOn(Schedulers.io())
    }

    override fun login(username: String, password: String): Single<User> {
        return service.login(username, password)
    }

    override fun find(id: Long): Single<User> {
        return service.find(id)
    }


}