package com.example.tp3_grupo7_be.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Perro(nombre: String?, imagen: String?, raza: String?, subRaza: String?, favorito: Boolean?,provincia: String?) :
    Parcelable {

    var nombre: String
    var imagen: String
    var raza: String
    var subRaza: String
    var favorito: Boolean
    var provincia: String

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString(),

        )

    init {
        this.nombre = nombre!!
        this.imagen = imagen!!
        this.raza = raza!!
        this.subRaza = subRaza!!
        this.favorito = favorito!!
        this.provincia = Provincias.BUENOS_AIRES.toString()
    }
    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(nombre)
        parcel.writeString(imagen)
        parcel.writeString(raza)
        parcel.writeString(subRaza)
        parcel.writeBoolean(favorito)
        parcel.writeString(provincia)
    }

    companion object CREATOR : Parcelable.Creator<Perro> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Perro {
            return Perro(parcel)
        }

        override fun newArray(size: Int): Array<Perro?> {
            return arrayOfNulls(size)
        }
    }

    class Provincias {
        companion object {
            val BUENOS_AIRES = "Buenos Aires"
            val CORDOBA = "Cordoba"
            val SANTA_FE = "Santa Fe"
        }
    }
}
