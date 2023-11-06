package com.example.tp3_grupo7_be.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.squareup.picasso.Picasso

class PerrosHolder(v: View): RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        this.view = v
    }

    fun setNombre(nombre: String) {
        val txt: TextView = view.findViewById(R.id.nombre_perro)
        txt.text = nombre
    }
    fun setImagen(imagen: String) {
        val img: ImageView = view.findViewById(R.id.imagen_perro)
        Picasso.get().load(imagen).into(img)
    }

    fun getCardLayout (): CardView {
        return view.findViewById(R.id.cardview_item_perros)
    }
}