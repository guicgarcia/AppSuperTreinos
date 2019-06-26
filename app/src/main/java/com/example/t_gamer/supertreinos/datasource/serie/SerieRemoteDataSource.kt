package com.example.t_gamer.supertreinos.datasource.serie

import com.example.t_gamer.supertreinos.datasource.serie.SerieDataSource
import com.example.t_gamer.supertreinos.entities.Serie
import com.example.t_gamer.supertreinos.network.SerieService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SerieRemoteDataSource: SerieDataSource {

    var service: SerieService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://guicgarcia.96.lt/treinos/admin/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create<SerieService>(SerieService::class.java)
    }

    override fun getAll(): Single<List<Serie>> {
        return service.getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun insert(serie: Serie): Single<Long> {
        return service.createSerie(serie.repetitions, serie.weight, serie.order)
            .map { serie.id }
            .subscribeOn(Schedulers.io())
    }

    override fun update(serie: Serie): Completable {
        return service.updateSerie(serie.id, serie.repetitions, serie.weight, serie.order)
            .subscribeOn(Schedulers.io())
    }

    override fun remove(exercise: Serie): Completable {
        return service.deleteSerie(exercise.id)
            .subscribeOn(Schedulers.io())
    }
}