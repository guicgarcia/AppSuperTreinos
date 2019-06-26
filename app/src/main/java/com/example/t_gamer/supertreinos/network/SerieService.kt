package com.example.t_gamer.supertreinos.network

import com.example.t_gamer.supertreinos.entities.Serie
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface SerieService {
        @Headers("Accept: application/json")
        @GET("series.json")
        fun getAll(): Single<List<Serie>>

        @Headers("Accept: application/json")
        @POST("series/add")
        @FormUrlEncoded
        fun createSerie(@Field("repetitions") repetitions: Int,
                           @Field("weight") weight: Double,
                           @Field("order_exercise") order: Int
                           ): Single<Serie>
//@Field("exercise_id") exercise_id: Int

        @Headers("Accept: application/json")
        @PATCH("series/edit/{id}")
        @FormUrlEncoded
        fun updateSerie(@Path("id") id: Long,
                           @Field("repetitions") repetitions: Int,
                           @Field("weight") weight: Double,
                           @Field("order_exercise") order: Int): Completable

        @Headers("Accept: application/json")
        @DELETE("series/delete/{id}")
        fun deleteSerie(@Path("id") id: Long): Completable
}
