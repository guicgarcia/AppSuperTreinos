package com.example.t_gamer.supertreinos.app.serie

import com.example.t_gamer.supertreinos.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_gamer.supertreinos.datasource.serie.SerieRepository
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.supertreinos.ui.serie.SerieAdapterListener
import com.example.t_gamer.supertreinos.ui.serie.SerieAdapter
import com.example.t_gamer.supertreinos.entities.Serie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_serie.*
import kotlinx.android.synthetic.main.content_todo.*

class SerieActivity : AppCompatActivity(), SerieAdapterListener {

    lateinit var adapter: SerieAdapter
    lateinit var serieRepository: SerieRepository
    lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie)

        configureRepository()
        loadSeries()

        btAddSerie.setOnClickListener {
            val position = adapter.createSerie()

            listUser.scrollToPosition(position)
        }

        swipeUsers.setOnRefreshListener {
            loadSeries()
        }

    }

    fun configureRepository() {
        disposables = CompositeDisposable()
        serieRepository = SerieRepository()
    }

    fun loadSeries() {
        swipeUsers.isRefreshing = true

        serieRepository
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ exercises ->
                swipeUsers.isRefreshing = false
                if (exercises != null)
                    loadRecyclerView(exercises)
            }, {
                Log.e("LOADSERIES", it.message, it)
            })
            .addTo(disposables)
    }

    private fun loadRecyclerView(series: List<Serie>) {
        adapter = SerieAdapter(series.toMutableList(), this)
        listUser.adapter = adapter
        listUser.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun SerieSaved(serie: Serie) {
        if (serie.id == 0L)
            serieRepository
                .insert(serie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ( { id -> serie.id = id }, {Log.e("MEU_ERRO_ADD", it.message, it)} )
                .addTo(disposables)
        else
            serieRepository
                .update(serie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposables)
    }

    override fun SerieRemoved(serie: Serie) {
        serieRepository
            .remove(serie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(disposables)
    }

}
