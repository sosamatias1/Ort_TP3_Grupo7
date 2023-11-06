package com.example.tp3_grupo7_be.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perros")
class Perro(nombre: String?, imagen: String?, raza: String?, subRaza: String?, favorito: Boolean?,provincia: String?, adoptado: Boolean?) :
    Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int
    @ColumnInfo(name = "nombre")
    var nombre: String
    @ColumnInfo(name = "imagen")
    var imagen: String
    @ColumnInfo(name = "raza")
    var raza: String
    @ColumnInfo(name = "subRaza")
    var subRaza: String
    @ColumnInfo(name = "favorito")
    var favorito: Boolean
    @ColumnInfo(name = "provincia")
    var provincia: String
    @ColumnInfo(name = "adoptado")
    var adoptado: Boolean

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString(),
        parcel.readBoolean(),

        )

    init {
        this.id = 0
        this.nombre = nombre!!
        this.imagen = imagen!!
        this.raza = raza!!
        this.subRaza = subRaza!!
        this.favorito = favorito!!
        this.provincia = provincia!!
        this.adoptado = adoptado!!
    }
    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(imagen)
        parcel.writeString(raza)
        parcel.writeString(subRaza)
        parcel.writeBoolean(favorito)
        parcel.writeString(provincia)
        parcel.writeBoolean(adoptado)
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
