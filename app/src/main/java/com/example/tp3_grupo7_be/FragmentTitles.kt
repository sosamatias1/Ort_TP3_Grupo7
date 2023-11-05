package com.example.tp3_grupo7_be

object FragmentTitles {
    val titlesMap: Map<Int, String> = mapOf(
        R.id.profile to "Perfil",
        R.id.settings to "Configuración",
        R.id.publicacionFragment to "Publicación"
    )

    public fun getTitleForFragment(fragmentId: Int): String {
        return titlesMap[fragmentId] ?: ""
    }
}