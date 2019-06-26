package com.example.t_gamer.supertreinos.ui.serie

import com.example.t_gamer.supertreinos.entities.Serie

interface SerieAdapterListener {

    fun SerieSaved(serie: Serie)
    fun SerieRemoved(serie: Serie)

}