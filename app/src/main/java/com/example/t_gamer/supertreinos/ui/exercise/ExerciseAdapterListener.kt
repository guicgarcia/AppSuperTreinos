package com.example.t_gamer.supertreinos.ui.exercise

import com.example.t_gamer.supertreinos.entities.Exercise

interface ExerciseAdapterListener {

    fun ExerciseSaved(exercise: Exercise)
    fun ExerciseRemoved(exercise: Exercise)
    fun exerciseView(id: Long)

}