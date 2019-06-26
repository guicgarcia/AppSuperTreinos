package com.example.t_gamer.supertreinos.ui.exercise

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.supertreinos.R
import com.example.t_gamer.supertreinos.app.serie.SerieActivity
import com.example.t_gamer.supertreinos.entities.Exercise
import com.example.t_gamer.supertreinos.entities.Serie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_exercise_view.view.*
import kotlinx.android.synthetic.main.item_exercise_edit.view.*
import kotlinx.android.synthetic.main.item_exercise_show.view.*

class ExerciseAdapter (var exercises: MutableList<Exercise>, var listener: ExerciseAdapterListener) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    private var exerciseEditing: Exercise? = null

    fun createExercise(user_id: Int): Int {
        Log.e("BOTAO2", "BOTAO2")
        val position = 0
        val exercise = Exercise("", "", "", user_id)
        exercises.add(position, exercise)
        exerciseEditing = exercise
        notifyItemInserted(position)
        return position
    }

    override fun getItemCount() = exercises.size

    override fun getItemViewType(position: Int): Int {
        //Pega posição do card selecionado
        val exercise = exercises[position]

        if(exercise != exerciseEditing) {
            return R.layout.item_exercise_show
        }else{
            return R.layout.item_exercise_edit
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExerciseViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.fillUI(exercises[position])
    }

    fun notify(exercise: Exercise, clear: Boolean = false) {
        val position = exercises.indexOf(exercise)
        exerciseEditing = if (clear) null else exercise
        notifyItemChanged(position)
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var serie: Serie? = null
        fun fillUI(exercise: Exercise) {

            if (exercise === exerciseEditing) {
                itemView.txtCadExercise.setText(exercise.name_exercise)
                itemView.txtImage.setText(exercise.image)
                itemView.txtDescriptionExercise.setText(exercise.description)
                    itemView.btSaveExercise.setOnClickListener {
                        exercise.name_exercise = itemView.txtCadExercise.text.toString()
                        exercise.image = itemView.txtImage.text.toString()
                        exercise.description = itemView.txtDescriptionExercise.text.toString()
                        if (exercise.name_exercise.isEmpty() || exercise.image.isEmpty() || exercise.description.isEmpty())
                            //Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
                        else {
                            listener.ExerciseSaved(exercise)
                            notify(exercise, true)
                        }
                    }
                    itemView.btDeleteExercise.setOnClickListener {
                        val position = exercises.indexOf(exercise)
                        notifyItemRemoved(position)
                        listener.ExerciseRemoved(exercise)
                        exercises.remove(exercise)
                        exerciseEditing = null
                    }
            } else {

                itemView.lblNameExercise.text = exercise.name_exercise
                Picasso.get().load(exercise.image).into(itemView.imageView)

                itemView.setOnClickListener {
                    notify(exercise)
                }
                itemView.setOnLongClickListener {
                    listener.ExerciseSaved(exercise)
                    notify(exercise, true)
                    true
                }

                itemView.imageView.setOnClickListener {
                    listener.exerciseView(exercise.id)
                }

            }

        }
    }

}