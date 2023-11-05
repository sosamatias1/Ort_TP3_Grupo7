package com.example.tp3_grupo7_be.service

import com.google.gson.annotations.SerializedName

data class ImagenPerroRespuesta(
    @SerializedName("message") val imagenes: List<String>,
    @SerializedName("status")  val status: String
)
