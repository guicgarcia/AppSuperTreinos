package com.example.t_gamer.supertreinos.datasource.exercise

import com.example.t_gamer.supertreinos.entities.Exercise
import com.example.t_gamer.supertreinos.entities.Serie
import io.reactivex.Completable
import io.reactivex.Single

interface ExerciseDataSource {

    fun getAll(): Single<List<Exercise>>
    fun insert(exercise: Exercise): Single<Long>
    fun update(exercise: Exercise): Completable
    fun remove(exercise: Exercise): Completable
    fun view(id: Long): Single<Exercise>
    fun listExercise(id: Long): Single<List<Exercise>>

}