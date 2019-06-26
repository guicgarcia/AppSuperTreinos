package com.example.t_gamer.supertreinos.app.exercise

import com.example.t_gamer.supertreinos.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise_view.*


class ExerciseViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_view)

        val getWeight0 = intent.getStringExtra("weight0")
        val getWeight1 = intent.getStringExtra("weight1")
        val getWeight2 = intent.getStringExtra("weight2")

        val getRepetitions0 = intent.getStringExtra("repetitions0")
        val getRepetitions1 = intent.getStringExtra("repetitions1")
        val getRepetitions2 = intent.getStringExtra("repetitions2")

        lblRepeticoes0.text = "Repetições: "+getRepetitions0.toString()
        lblPeso0.text = "Peso: "+getWeight0.toString()

        lblRepeticoes1.text = "Repetições: "+getRepetitions1.toString()
        lblPeso1.text = "Peso: "+getWeight1.toString()

        lblRepeticoes2.text = "Repetições: "+getRepetitions2.toString()
        lblPeso2.text = "Peso: "+getWeight2.toString()

    }



}
