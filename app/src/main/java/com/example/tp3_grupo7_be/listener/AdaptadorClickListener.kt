package com.example.tp3_grupo7_be.listener

import com.example.tp3_grupo7_be.models.Perro

interface AdaptadorClickListener {

     fun onCheckBoxCheckedChange(perro: Perro, isChecked: Boolean)

     fun onViewItemDetail(perro: Perro)

     fun onFilterSelected(filter: String)

     fun onFilterRemoved()
}