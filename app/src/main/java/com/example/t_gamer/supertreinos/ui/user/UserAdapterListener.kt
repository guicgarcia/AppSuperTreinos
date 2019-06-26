package com.example.t_gamer.supertreinos.ui.user

import com.example.t_gamer.supertreinos.entities.User

interface UserAdapterListener {

    fun userSaved(user: User)
    fun userRemoved(user: User)

}

