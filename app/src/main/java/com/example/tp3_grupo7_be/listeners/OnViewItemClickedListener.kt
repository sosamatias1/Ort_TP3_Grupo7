package com.example.tp3_grupo7_be.listeners

import com.example.tp3_grupo7_be.models.Perro

interface OnViewItemClickedListener {
    fun onViewItemDetail(perro: Perro)
}