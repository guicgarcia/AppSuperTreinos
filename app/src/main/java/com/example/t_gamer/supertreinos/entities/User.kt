package com.example.t_gamer.supertreinos.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    var name: String,
    var email: String,
    var username: String,
    var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun equals(other: Any?) =
        other != null && (this === other || (this.id != 0L && this.id == (other as User).id))
}