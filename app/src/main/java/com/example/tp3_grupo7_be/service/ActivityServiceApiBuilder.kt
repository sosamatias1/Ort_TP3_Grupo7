package com.example.tp3_grupo7_be.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ActivityServiceApiBuilder {

    private val BASE_URL = "https://dog.ceo/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): PerroService{
        return retrofit.create(PerroService::class.java)
    }
}