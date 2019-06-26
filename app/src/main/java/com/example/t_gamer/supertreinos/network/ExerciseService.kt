package com.example.t_gamer.supertreinos.network

import com.example.t_gamer.supertreinos.entities.Exercise
import com.example.t_gamer.supertreinos.entities.Serie
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface  ExerciseService {
    @Headers("Accept: application/json")
    @GET("exercises.json")
    fun getAll(): Single<List<Exercise>>

    @Headers("Accept: application/json")
    @POST("exercises/add")
    @FormUrlEncoded
    fun createExercise(@Field("name_exercise") name_exercise: String,
                        @Field("image") image: String,
                        @Field("description") description: String,
                        @Field("user_id") user_id: Int): Single<Exercise>


    @Headers("Accept: application/json")
    @PATCH("exercises/edit/{id}")
    @FormUrlEncoded
    fun updateExercise(@Path("id") id: Long,
                   @Field("name_exercise") name_exercise: String,
                   @Field("image") image: String,
                   @Field("description")description: String): Completable

    @Headers("Accept: application/json")
    @DELETE("exercises/delete/{id}")
    fun deleteExercise(@Path("id") id: Long): Completable

    @Headers("Accept: application/json")
    @GET("exercises/view/{id}")
    fun viewExercise(@Path("id") id: Long): Single<Exercise>

    @Headers("Accept: application/json")
    @GET("exercises/index/{id}")
    fun listExercise(@Path("id") id: Long): Single<List<Exercise>>
}