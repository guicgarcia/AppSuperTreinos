package com.example.t_gamer.supertreinos.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.t_gamer.supertreinos.*
import kotlinx.android.synthetic.main.activity_login.*
import com.example.t_gamer.supertreinos.app.exercise.ExerciseActivity
import com.example.t_gamer.supertreinos.datasource.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class LoginActivity : AppCompatActivity() {

    lateinit var userRepository: UserRepository
    lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUserName.setText(intent.getStringExtra("username"))

        configureRepository()

        btLogar.setOnClickListener {
            login()
        }

        btCadUser.setOnClickListener {
            RegisterUser()
        }

    }

    fun configureRepository() {
        disposables = CompositeDisposable()
        userRepository = UserRepository()
    }

    private fun login() {

        val username = txtUserName.text.toString()
        val password = txtPassword.text.toString()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Preencha todos os dados", Toast.LENGTH_LONG).show()
        } else {
            userRepository.login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                if (user.id.toString().equals("0")) {
                    Toast.makeText(this, "Email ou Senha Incorretos", Toast.LENGTH_LONG).show()
                    clear()
                } else {
                    val intent = Intent(this, ExerciseActivity::class.java)
                    intent.putExtra("user", user.id.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }.addTo(disposables)
        }
    }

    private fun clear() {
        txtUserName.text.clear()
        txtPassword.text.clear()
    }

    private fun RegisterUser() {
        val intentRegUser = Intent(this, RegisterActivity::class.java)
        startActivity(intentRegUser)
    }

}





