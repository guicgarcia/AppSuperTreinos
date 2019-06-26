package com.example.t_gamer.supertreinos.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    var name_exercise: String,
    var image: String,
    var description: String,
    var user_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userId: Long = 0

    var series: List<Serie>? = null

    override fun equals(other: Any?) =
        other != null && (this === other || (this.id != 0L && this.id == (other as Exercise).id))
}