package com.example.tp3_grupo7_be.holders

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.squareup.picasso.Picasso

class PerrosHolder(v: View): RecyclerView.ViewHolder(v) {

    private var view: View
    val checkBox: CheckBox = itemView.findViewById(R.id.favorito_perro)


    init {
        this.view = v

    }
    fun setId(id: Int){
        val txt: TextView = view.findViewById(R.id.id_perro)
        txt.text = id.toString()
    }
    fun setNombre(nombre: String) {
        val txt: TextView = view.findViewById(R.id.nombre_perro)
        txt.text = nombre
    }
    fun setImagen(imagen: String) {
        val img: ImageView = view.findViewById(R.id.imagen_perro)
        Picasso.get()
            .load(imagen)
            .fit()
            .centerInside()
            .into(img)
    }
    fun setRaza(raza: String) {
        val txt: TextView = view.findViewById(R.id.raza_perro)
        txt.text = raza
    }

    fun getCardLayout (): CardView {
        return view.findViewById(R.id.cardview_item_perros)
    }
    fun setSubRaza(subRaza: String) {
        val txt: TextView = view.findViewById(R.id.subraza_perro)
        txt.text = subRaza
    }
    fun setFavorito(favorito: Boolean) {
        val checkBox1: CheckBox = view.findViewById(R.id.favorito_perro)
        checkBox1.isChecked = favorito
        }

}