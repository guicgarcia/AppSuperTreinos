package com.example.t_gamer.supertreinos.datasource.serie

import com.example.t_gamer.supertreinos.entities.Serie
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SerieRepository: SerieDataSource {

    val dataSource: SerieDataSource
        get() = SerieRemoteDataSource()

    override fun getAll() = dataSource
        .getAll()
        .subscribeOn(Schedulers.io())

    override fun insert(serie: Serie) = dataSource
        .insert(serie)
        .subscribeOn(Schedulers.io())

    override fun update(serie: Serie): Completable = dataSource
        .update(serie)
        .subscribeOn(Schedulers.io())

    override fun remove(serie: Serie) = dataSource
        .remove(serie)
        .subscribeOn(Schedulers.io())
}