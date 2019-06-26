package com.example.t_gamer.supertreinos.ui.serie

import com.example.t_gamer.supertreinos.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.example.t_gamer.supertreinos.entities.Serie
import kotlinx.android.synthetic.main.activity_exercise_view.view.*
import kotlinx.android.synthetic.main.item_serie_edit.view.*
import kotlinx.android.synthetic.main.item_serie_show.view.*

class SerieAdapter(var series: MutableList<Serie>, var listener: SerieAdapterListener) :
    RecyclerView.Adapter<SerieAdapter.SerieViewHolder>() {

    private var serieEditing: Serie? = null

    fun createSerie(): Int {
        val position = 0
        val serie = Serie()
        series.add(position, serie)
        serieEditing = serie
        notifyItemInserted(position)
        return position
    }

    override fun getItemCount() = series.size

    override fun getItemViewType(position: Int) =
        if (series[position] === serieEditing)
            R.layout.item_serie_edit
        else
            R.layout.item_serie_show

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SerieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.fillUI(series[position])
    }

    fun notify(serie: Serie, clear: Boolean = false) {
        val position = series.indexOf(serie)
        serieEditing = if (clear) null else serie
        notifyItemChanged(position)
    }

    inner class SerieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillUI(serie: Serie) {

            if (serie === serieEditing) {
                itemView.txtRepeticoes.setText("" + serie.repetitions)
                itemView.txtPeso.setText("" + serie.weight)
                itemView.txtOrdem.setText("" + serie.order)
                itemView.btSaveSerie.setOnClickListener {
                    serie.repetitions = itemView.txtRepeticoes.text.toString().toInt()
                    serie.weight = itemView.txtRepeticoes.text.toString().toDouble()
                    serie.order = itemView.txtOrdem.text.toString().toInt()
                    //serie.exercise_id = 1
                    listener.SerieSaved(serie)
                    notify(serie, true)
                    Log.e("BOTAO", "BOTAO")
                }
                itemView.btDeleteSerie.setOnClickListener {
                    val position = series.indexOf(serie)
                    notifyItemRemoved(position)
                    listener.SerieRemoved(serie)
                    series.remove(serie)
                    serieEditing = null
                }
            } else {

                itemView.lblNameSerie.text = serie.repetitions.toString()

                itemView.setOnClickListener {
                    notify(serie)
                }
                itemView.setOnLongClickListener {
                    listener.SerieSaved(serie)
                    notify(serie, true)
                    true
                }
            }

        }
    }

}