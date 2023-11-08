package com.example.tp3_grupo7_be.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PerroService {
    @GET("api/breeds/list/all")
    fun getRazas(): Call<RazaPerroRespuesta>

    @GET("api/breed/{raza}/images")
    fun getImagenes(@Path("raza") raza: String): Call<ImagenPerroRespuesta>

    @GET("api/breed/{raza}/{subraza}/images")
    fun getImagenes(@Path("raza") raza: String, @Path("subraza") subraza: String): Call<ImagenPerroRespuesta>

}