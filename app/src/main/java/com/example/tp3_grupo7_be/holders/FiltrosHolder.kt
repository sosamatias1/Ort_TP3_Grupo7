package com.example.tp3_grupo7_be.holders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.squareup.picasso.Picasso

class FiltrosHolder (v: View): RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        this.view = v
    }
    fun getCardLayout (): CardView {
        return view.findViewById(R.id.card_filtro)
    }
    fun setNombre(nombre: String) {
        val txt: TextView = view.findViewById(R.id.filtro_nombre)
        txt.text = nombre
    }
}