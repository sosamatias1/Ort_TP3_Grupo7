package com.example.tp3_grupo7_be.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3_grupo7_be.R
import com.example.tp3_grupo7_be.holders.FiltrosHolder
import com.example.tp3_grupo7_be.listener.AdaptadorClickListener

class FiltrosAdapter(private var list: MutableList<String>) : RecyclerView.Adapter<FiltrosHolder>() {

    private var clickListener: AdaptadorClickListener? = null
    private var filtroSeleccionado: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltrosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filtro, parent, false)
        return FiltrosHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FiltrosHolder, position: Int) {
        val filtro = list[position]
        holder.setNombre(filtro)

        if (filtro == filtroSeleccionado) {
            holder.getCardLayout().setCardBackgroundColor(R.color.lila.toInt())
        } else {
            holder.getCardLayout().setCardBackgroundColor(R.color.black.toInt())
        }

        holder.getCardLayout().setOnClickListener {
            if (filtroSeleccionado != filtro) {
                val posicionVieja = list.indexOf(filtroSeleccionado)
                filtroSeleccionado = filtro
                notifyItemChanged(posicionVieja)
                notifyItemChanged(position)
                clickListener?.onFilterSelected(filtro)
            } else {
                filtroSeleccionado = null
                notifyItemChanged(position)
                clickListener?.onFilterRemoved()
            }
        }
    }

    fun setClickListener(listener: AdaptadorClickListener) {
        clickListener = listener
    }

    fun updateData(newList: MutableList<String>) {
        list = newList
        notifyDataSetChanged()
    }
}



