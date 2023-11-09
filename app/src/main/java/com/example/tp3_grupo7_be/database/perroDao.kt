package com.example.tp3_grupo7_be.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tp3_grupo7_be.models.Perro

@Dao
interface perroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerro(perro: Perro?)

    @Update
    fun updatePerro(perro: Perro?)

    @Delete
    fun delete(perro: Perro?)

    @Query("SELECT * FROM perros WHERE id = :id")
    fun loadPerrosById(id: Int): Perro?

    @Query("SELECT * FROM perros WHERE adoptado = false ORDER BY id")
    fun loadAllPerrosNoAdoptados(): MutableList<Perro>

    @Query("SELECT * FROM perros WHERE adoptado = false AND favorito = true ORDER BY id" )
    fun loadAllPerrosNoAdoptadosFavoritos(): MutableList<Perro>

    @Query("SELECT * FROM perros WHERE adoptado = true ORDER BY id")
    fun loadAllPerrosAdoptados(): MutableList<Perro>

    @Query("UPDATE perros SET adoptado = true WHERE id = :id")
    fun updateAdoptadoPerro(id: Int)

    @Query("UPDATE perros SET favorito = :favorito WHERE id = :id")
    fun updateFavoritoPerro(favorito: Boolean, id: Int)

    @Query("SELECT * FROM perros WHERE raza = :raza ORDER BY id")
    fun loadAllPerrosPorRaza(raza: String): MutableList<Perro>

    @Query("SELECT * FROM perros WHERE nombre = :nombre ORDER BY id")
    fun loadAllPerrosPorNombre(nombre: String): MutableList<Perro>


}