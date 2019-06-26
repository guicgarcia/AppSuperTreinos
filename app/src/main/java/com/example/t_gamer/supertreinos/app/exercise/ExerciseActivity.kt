package com.example.t_gamer.supertreinos.app.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_gamer.supertreinos.*
import com.example.t_gamer.supertreinos.entities.Exercise
import com.example.t_gamer.supertreinos.entities.Serie
import com.example.t_gamer.supertreinos.ui.exercise.ExerciseAdapterListener
import com.example.t_gamer.supertreinos.ui.exercise.ExerciseAdapter
import com.example.t_gamer.supertreinos.datasource.exercise.ExerciseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.content_todo.*
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.supertreinos.app.serie.SerieActivity
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.content_exercise.*
import com.example.t_gamer.supertreinos.entities.User
import com.example.t_gamer.supertreinos.datasource.user.UserRepository
import kotlinx.android.synthetic.main.activity_exercise_view.*
import kotlinx.android.synthetic.main.item_exercise_show.*
import java.io.Serializable

class ExerciseActivity : AppCompatActivity(), ExerciseAdapterListener {

    lateinit var adapter: ExerciseAdapter
    lateinit var exerciseRepository: ExerciseRepository
    lateinit var userRepository: UserRepository
    lateinit var disposables: CompositeDisposable

    companion object {
        var sessionUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        configureRepository()
        sessaoUser()
        loadExercises()

        btAddExercise.setOnClickListener {
            val userId = intent.getStringExtra("user").toLong().toInt()

            val position = adapter.createExercise(userId)

            listUser.scrollToPosition(position)
        }

        swipeUsers.setOnRefreshListener {
            loadExercises()
        }
    }

    fun configureRepository() {
        disposables = CompositeDisposable()
        exerciseRepository = ExerciseRepository()
        userRepository = UserRepository()
    }

    private fun sessaoUser() {
        val userId = intent.getStringExtra("user").toLong()
        Log.e("IdUser", userId.toString())
        userRepository.find(userId).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                    user->ExerciseActivity.sessionUser = user
                    configureRepository()
                    loadExercises()
                Log.e("SESSAOUSER", sessionUser.toString())
            },
            {
                Log.e("ERROLOGIN", "LOGINERRO")
            })
        .addTo(disposables)
    }

    fun loadExercises() {
        swipeUsers.isRefreshing = true

        val userId = intent.getStringExtra("user").toLong()

        exerciseRepository
            .listExercise(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ exercises ->
                swipeUsers.isRefreshing = false
                if (exercises != null)
                    loadRecyclerView(exercises)
            }, {
                Log.e("LOADEXERCISES", it.message, it)
            })
            .addTo(disposables)
    }

    private fun loadRecyclerView(exercises: List<Exercise>) {
        adapter = ExerciseAdapter(exercises.toMutableList(), this)
        listUser.adapter = adapter
        listUser.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun ExerciseSaved(exercise: Exercise) {
        if (exercise.id == 0L)
            exerciseRepository
                .insert(exercise)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                        id -> exercise.id = id
                    },
                    {Log.e("MEU_ERRO_ADD", it.message, it)} )
                .addTo(disposables)
        else
            exerciseRepository
                .update(exercise)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposables)

        val intentSeries = Intent(this, SerieActivity::class.java)
        startActivity(intentSeries)
    }

    override fun ExerciseRemoved(exercise: Exercise) {
        exerciseRepository
            .remove(exercise)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(disposables)
    }

    override fun exerciseView(id: Long) {
        exerciseRepository
            .view(id)
            .observeOn(AndroidSchedulers.mainThread()).subscribe ({

                    exer ->

                                if(exer.series?.size == 0) {
                                    val intent = Intent(this, ExerciseViewActivity::class.java)
                                    intent.putExtra("weight0", "Não possuí")
                                    intent.putExtra("weight1", "Não possuí")
                                    intent.putExtra("weight2", "Não possuí")

                                    intent.putExtra("repetitions0", "Não possuí")
                                    intent.putExtra("repetitions1", "Não possuí")
                                    intent.putExtra("repetitions2", "Não possuí")
                                    startActivity(intent)
                                } else {
                                    val repetitions0 = exer.series?.get(0)?.repetitions.toString()
                                    val repetitions1 = exer.series?.get(1)?.repetitions.toString()
                                    val repetitions2 = exer.series?.get(2)?.repetitions.toString()

                                    val weight0 = exer.series?.get(0)?.weight.toString()
                                    val weight1 = exer.series?.get(1)?.weight.toString()
                                    val weight2 = exer.series?.get(2)?.weight.toString()

                                    val intent = Intent(this, ExerciseViewActivity::class.java)
                                    intent.putExtra("weight0", weight0)
                                    intent.putExtra("weight1", weight1)
                                    intent.putExtra("weight2", weight2)

                                    intent.putExtra("repetitions0", repetitions0)
                                    intent.putExtra("repetitions1", repetitions1)
                                    intent.putExtra("repetitions2", repetitions2)
                                    startActivity(intent)
                                }


            }, {Log.e("DEUERRO", it.message, it)})
            .addTo(disposables)
    }

}
