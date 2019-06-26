package com.example.t_gamer.supertreinos.datasource.serie

import io.reactivex.Single
import com.example.t_gamer.supertreinos.entities.Serie
import io.reactivex.Completable

interface SerieDataSource {

    fun getAll(): Single<List<Serie>>
    fun insert(serie: Serie): Single<Long>
    fun update(serie: Serie): Completable
    fun remove(serie: Serie): Completable

}