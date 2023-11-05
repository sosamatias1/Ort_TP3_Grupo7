package com.example.tp3_grupo7_be.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.holders.PerrosHolder
import com.example.tp3_grupo7_be.models.Perro

class PerrosAdapter(private val list: MutableList<Perro>): Adapter<PerrosHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerrosHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_perros,parent,false)

        return(PerrosHolder(view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PerrosHolder, position: Int) {

        val perro = list[position]
        holder.setNombre(perro.nombre)
        holder.setImagen(perro.imagen)
    }
}