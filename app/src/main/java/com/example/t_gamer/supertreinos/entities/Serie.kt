package com.example.t_gamer.supertreinos.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class Serie(
    var repetitions: Int = 0,
    var weight: Double = 0.0,
    var order: Int = 0
    //var exercise_id: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var exercise_id: Long = 0

    override fun equals(other: Any?) =
        other != null && (this === other || (this.id != 0L && this.id == (other as Serie).id))
}