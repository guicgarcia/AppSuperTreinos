package com.example.t_gamer.supertreinos.app.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.supertreinos.*
import com.example.t_gamer.supertreinos.datasource.user.UserRepository
import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.ui.user.UserAdapter
import com.example.t_gamer.supertreinos.ui.user.UserAdapterListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.content_todo.*

class UserActivity : AppCompatActivity(), UserAdapterListener {

    lateinit var adapter: UserAdapter
    lateinit var userRepository: UserRepository
    lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
//        setSupportActionBar(toolbar)

        configureRepository()
        loadUsers()

        btAdd.setOnClickListener {
            val position = adapter.createUser()
            listUser.scrollToPosition(position)
        }

        swipeUsers.setOnRefreshListener {
            loadUsers()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun configureRepository() {
        disposables = CompositeDisposable()
        userRepository = UserRepository()
    }

    fun loadUsers() {
        swipeUsers.isRefreshing = true

        userRepository
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users ->
                swipeUsers.isRefreshing = false
                if (users != null)
                    loadRecyclerView(users)
            }, {
                Log.e("MEU_ERRO LOAD", it.message, it)
            })
            .addTo(disposables)
    }

    private fun loadRecyclerView(users: List<User>) {
        adapter = UserAdapter(users.toMutableList(), this)
        listUser.adapter = adapter
        listUser.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun userSaved(user: User) {
        if (user.id == 0L)
            userRepository
                .insert(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ( { id -> user.id = id }, {Log.e("MEU_ERRO ADD", it.message, it)} )
                .addTo(disposables)
        else
            userRepository
                .update(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposables)
    }

    override fun userRemoved(user: User) {
        userRepository
            .remove(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(disposables)
    }

}
