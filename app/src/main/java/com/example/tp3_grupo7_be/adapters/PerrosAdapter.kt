package com.example.tp3_grupo7_be.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.holders.PerrosHolder
import com.example.tp3_grupo7_be.listener.AdaptadorClickListener
import com.example.tp3_grupo7_be.models.Perro


class PerrosAdapter(private val list: MutableList<Perro>) : Adapter<PerrosHolder>() {

    private var clickListener: AdaptadorClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerrosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perros, parent, false)

        return PerrosHolder(view).apply {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val perro = list[position]
                    clickListener?.onCheckBoxCheckedChange(perro, isChecked)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PerrosHolder, position: Int) {

        val perro = list[position]

        holder.setNombre(perro.nombre)
        holder.setImagen(perro.imagen)

        holder.getCardLayout().setOnClickListener{
            clickListener?.onViewItemDetail(perro)
        }
        holder.setRaza(perro.raza)
        holder.setSubRaza(perro.subRaza)
        holder.setFavorito(perro.favorito)
        holder.setEdad(perro.edad)
        holder.setGenero(perro.genero)

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
          clickListener?.onCheckBoxCheckedChange(perro, isChecked)
        }
    }

    fun setClickListener(listener: AdaptadorClickListener) {
        clickListener = listener
    }
}



