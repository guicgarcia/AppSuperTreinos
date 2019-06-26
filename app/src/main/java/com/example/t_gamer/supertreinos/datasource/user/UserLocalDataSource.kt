package com.example.t_gamer.supertreinos.datasource.user

import androidx.room.Room
import com.example.t_gamer.supertreinos.db.AppDatabase
import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.db.dao.UserDao
import com.example.t_gamer.supertreinos.app.user.UserApplication
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserLocalDataSource : UserDataSource {

    var userDao: UserDao

    init {
        val db =
            Room.databaseBuilder(
                UserApplication.appContext,
                AppDatabase::class.java,
                "todo.db"
            )
                .allowMainThreadQueries()
                .build()
        userDao = db.userDao()
    }

    override fun getAll(): Single<List<User>> {
        return userDao.getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun insert(user: User): Single<Long> {
        return userDao.insert(user)
            .subscribeOn(Schedulers.io())
    }

    override fun update(user: User): Completable {
        return userDao.update(user)
            .subscribeOn(Schedulers.io())
    }

    override fun remove(user: User): Completable {
        return userDao.remove(user)
            .subscribeOn(Schedulers.io())
    }

    override fun login(userName: String, password: String): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(id: Long): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}