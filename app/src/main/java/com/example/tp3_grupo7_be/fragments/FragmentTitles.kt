package com.example.tp3_grupo7_be.fragments

import com.example.tp3_grupo7_be.R

object FragmentTitles {
    val titlesMap: Map<Int, String> = mapOf(
        R.id.profile to "Perfil",
        R.id.settings to "Configuración",
        R.id.publicacionFragment to "Publicación",
        R.id.favoritosFragment to "Favoritos",
        R.id.homeFragment to "Home",
        R.id.adopcionFragment to "Adoptados"

    )

    public fun getTitleForFragment(fragmentId: Int): String {
        return titlesMap[fragmentId] ?: ""
    }
}