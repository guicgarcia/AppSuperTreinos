package com.example.t_gamer.supertreinos.network

import com.example.t_gamer.supertreinos.entities.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET

interface UserService {
    @Headers("Accept: application/json")
    @GET("users.json")
    fun getAll(): Single<List<User>>

    @Headers("Accept: application/json")
    @POST("users/add")
    @FormUrlEncoded
    fun createUser(@Field("name") nome: String,
                   @Field("email") email: String,
                   @Field("username")username: String,
                   @Field("password")password: String ): Single<User>


    @Headers("Accept: application/json")
    @PATCH("users/edit/{id}")
    @FormUrlEncoded
    fun updateUser(@Path("id") id: Long,
                   @Field("name") nome: String,
                   @Field("email") email: String,
                   @Field("username")username: String,
                   @Field("password")password: String ): Completable

    @Headers("Accept: application/json")
    @DELETE("users/delete/{id}")
    fun deleteUser(@Path("id") id: Long): Completable

    @Headers("Accept: application/json")
    @POST("users/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Single<User>

    @Headers("Accept: application/json")
    @GET("users/view/{id}")
    fun find(@Path("id") id: Long) : Single<User>
}