package com.example.t_gamer.supertreinos.datasource.exercise

import com.example.t_gamer.supertreinos.entities.Exercise
import com.example.t_gamer.supertreinos.entities.Serie
import com.example.t_gamer.supertreinos.network.ExerciseService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ExerciseRemoteDataSource: ExerciseDataSource {

    var service: ExerciseService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://guicgarcia.96.lt/treinos/admin/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create<ExerciseService>(ExerciseService::class.java)
    }

    override fun getAll(): Single<List<Exercise>> {
        return service.getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun insert(exercise: Exercise): Single<Long> {
        return service.createExercise(exercise.name_exercise, exercise.image, exercise.description, exercise.user_id)
            .map { exercise.id }
            .subscribeOn(Schedulers.io())
    }

    override fun update(exercise: Exercise): Completable {
        return service.updateExercise(exercise.id, exercise.name_exercise, exercise.image, exercise.description)
            .subscribeOn(Schedulers.io())
    }

    override fun remove(exercise: Exercise): Completable {
        return service.deleteExercise(exercise.id)
            .subscribeOn(Schedulers.io())
    }

//    override fun view(id: Int): Completable {
//        return service.viewExercise(id)
//            .subscribeOn(Schedulers.io())
//    }

    override fun view(id: Long): Single<Exercise> {
        return service.viewExercise(id)
    }

    override fun listExercise(id: Long): Single<List<Exercise>> {
        return service.listExercise(id)
    }

}