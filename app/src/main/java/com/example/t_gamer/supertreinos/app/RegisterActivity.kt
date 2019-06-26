package com.example.t_gamer.supertreinos.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.t_gamer.supertreinos.*
import com.example.t_gamer.supertreinos.datasource.user.UserRepository
import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.ui.user.UserAdapterListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var userRepository: UserRepository
    lateinit var disposables: CompositeDisposable
    //lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        configureRepository()

        btCadUsuario.setOnClickListener {
            userSaved()
        }

    }

    fun configureRepository() {
        disposables = CompositeDisposable()
        userRepository = UserRepository()
    }

    private fun cadUser() {
        Toast.makeText(this, "Conta Cadastrada com Sucesso!", Toast.LENGTH_LONG).show()
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

     fun userSaved() {
        val name =  txtCad.text.toString()
        val email = txtXCadEmail.text.toString()
        val userName = txtCadUserName.text.toString()
        val password = txtCadPassword.text.toString()

         if (name.isEmpty() || email.isEmpty() || userName.isEmpty() || password.isEmpty())
             Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
         else {

             val user = User(name, email, userName, password)

             if (user.id == 0L)
                 userRepository
                     .insert(user)
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe({ id -> user.id = id }, { Log.e("MEU_ERRO_ADD", it.message, it) })
                     .addTo(disposables)
             else
                 userRepository
                     .update(user)
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe()
                     .addTo(disposables)
             val intent = Intent(this, LoginActivity::class.java)
             intent.putExtra("username", email)
             Toast.makeText(this, "Usuário Cadastrado com sucesso!", Toast.LENGTH_LONG).show()
             startActivity(intent)
         }
    }


}
