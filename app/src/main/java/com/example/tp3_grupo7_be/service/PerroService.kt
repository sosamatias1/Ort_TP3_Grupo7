package com.example.tp3_grupo7_be.service

import retrofit2.Call
import retrofit2.http.GET

interface PerroService {
    @GET("api/breeds/list/all")
    fun getRazas(): Call<ImagenPerroRespuesta>

    @GET("api/breed/hound/images")
    fun getImagenes(): Call<ImagenPerroRespuesta>

}