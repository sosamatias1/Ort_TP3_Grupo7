package com.example.tp3_grupo7_be.models

class Perro(nombre: String, imagen: String,raza: String, subRaza: String, favorito: Boolean) {
    var nombre: String
    var imagen: String
    var raza: String
    var subRaza: String
    var favorito: Boolean

    init{
        this.nombre = nombre
        this.imagen = imagen
        this.raza = raza
        this.subRaza = subRaza
        this.favorito = favorito
    }
}