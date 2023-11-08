package com.example.tp3_grupo7_be.service

import com.google.gson.annotations.SerializedName

data class RazaPerroRespuesta(
    @SerializedName("message") val razas: Map<String,List<String>>,
    @SerializedName("status")  val status: String

)
