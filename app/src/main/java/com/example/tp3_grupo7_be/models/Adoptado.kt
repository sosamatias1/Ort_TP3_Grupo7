package com.example.tp3_grupo7_be.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adoptados")
class Adoptado(
    idPerro: Int?,
    duenio: String?
) :
    Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "idPerro")
    var idPerro: Int

    @ColumnInfo(name = "duenio")
    var duenio: String


    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    init {
        this.idPerro = idPerro!!
        this.duenio = duenio!!
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idPerro)
        parcel.writeString(duenio)
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
    enum class Provincias(val provincia: String) {
        BUENOS_AIRES("Buenos Aires"),
        CORDOBA("Cordoba"),
        SANTA_FE("Santa Fe")
    }

    fun getProvinciasList(): List<String> {
        return Provincias.values().map { it.provincia }
    }

    enum class Generos(val genero: String) {
        MACHO("Macho"),
        HEMBRA("Hembra")
    }

    fun getGenerosList(): List<String> {
        return Generos.values().map { it.genero }
    }

}
