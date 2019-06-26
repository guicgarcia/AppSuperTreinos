package com.example.t_gamer.supertreinos.datasource.exercise

import com.example.t_gamer.supertreinos.entities.Exercise
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ExerciseRepository: ExerciseDataSource {

    val dataSource: ExerciseDataSource
        get() = ExerciseRemoteDataSource()

    override fun getAll() = dataSource
        .getAll()
        .subscribeOn(Schedulers.io())

    override fun insert(exercise: Exercise) = dataSource
        .insert(exercise)
        .subscribeOn(Schedulers.io())

    override fun update(exercise: Exercise): Completable = dataSource
        .update(exercise)
        .subscribeOn(Schedulers.io())

    override fun remove(exercise: Exercise) = dataSource
        .remove(exercise)
        .subscribeOn(Schedulers.io())

//    override fun view(id: Int) = dataSource
//        .view(id)
//        .subscribeOn(Schedulers.io())

    override fun view(id: Long): Single<Exercise> {
        return dataSource.view(id).subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
    }

    override fun listExercise(id: Long) = dataSource
        .listExercise(id)
        .subscribeOn(Schedulers.io())


//    override fun listExercise(id: Long): Single<Exercise> {
//        return dataSource.listExercise(id).subscribeOn(Schedulers.io())
//    }

}