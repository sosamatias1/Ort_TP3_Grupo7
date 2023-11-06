package com.example.tp3_grupo7_be.models

import android.os.Parcel
import android.os.Parcelable

class Perro(nombre: String?, imagen: String?) : Parcelable {
    var nombre: String
    var imagen: String

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    init {
        this.nombre = nombre!!
        this.imagen = imagen!!
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(nombre)
        parcel.writeString(imagen)
    }

    companion object CREATOR : Parcelable.Creator<Perro> {
        override fun createFromParcel(parcel: Parcel): Perro {
            return Perro(parcel)
        }

        override fun newArray(size: Int): Array<Perro?> {
            return arrayOfNulls(size)
        }
    }
}