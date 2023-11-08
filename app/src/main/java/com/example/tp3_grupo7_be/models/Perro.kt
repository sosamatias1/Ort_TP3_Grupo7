package com.example.tp3_grupo7_be.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "perros")
class Perro(
    nombre: String?,
    imagen: MutableList<String>?,
    raza: String?,
    subRaza: String?,
    provincia: String?,
    edad: Int?,
    genero: String?,
    nombreDuenio: String?,
    peso: Int
) :
    Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

    @ColumnInfo(name = "nombre")
    var nombre: String

    @TypeConverters(StringListConverter::class)
    @ColumnInfo(name = "imagen")
    var imagen: MutableList<String>

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

    @ColumnInfo(name = "edad")
    var edad: Int

    @ColumnInfo(name = "genero")
    var genero: String

    @ColumnInfo(name = "nombreDuenio")
    var nombreDuenio: String

    @ColumnInfo(name = "nombreAdoptante")
    var nombreAdoptante: String?

    @ColumnInfo(name = "peso")
    var peso: Int

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
    )

    init {
        this.id = 0
        this.nombre = nombre!!
        this.imagen = imagen!!
        this.raza = raza!!.replaceFirstChar { it.uppercaseChar() }
        if (subRaza == null) {

            this.subRaza = "Sin subraza"
        } else {
            this.subRaza = subRaza!!.replaceFirstChar { it.uppercaseChar() }
        }
        this.favorito = false
        this.provincia = provincia!!
        this.adoptado = false
        this.edad = edad!!
        this.genero = genero!!
        this.nombreDuenio = nombreDuenio!!
        this.nombreAdoptante = null
        this.peso = peso!!
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeStringList(imagen)
        parcel.writeString(raza)
        parcel.writeString(subRaza)
        parcel.writeBoolean(favorito)
        parcel.writeString(provincia)
        parcel.writeBoolean(adoptado)
        parcel.writeInt(edad)
        parcel.writeString(genero)
        parcel.writeString(nombreDuenio)
        parcel.writeString(nombreAdoptante)
        parcel.writeInt(peso)
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

    class Generos {
        companion object {
            val MACHO = "Macho"
            val HEMBRA = "Hembra"
        }
    }


}
