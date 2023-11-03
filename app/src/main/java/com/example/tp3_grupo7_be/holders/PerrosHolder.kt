package com.example.tp3_grupo7_be.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R

class PerrosHolder(v: View): RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        this.view = v
    }

    fun setNombre(nombre: String) {
        val txt: TextView = view.findViewById(R.id.nombre_perro)
        txt.text = nombre
    }
}