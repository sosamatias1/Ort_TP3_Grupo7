package com.example.tp3_grupo7_be.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tp3_grupo7_be.models.Adoptado

@Dao
interface adoptadoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAdoptado(adoptado: Adoptado?)

    @Update
    fun updateAdoptado(adoptado: Adoptado?)

    @Delete
    fun delete(adoptado: Adoptado?)

    @Query("SELECT * FROM adoptados WHERE idPerro = :idPerro")
    fun loadAdoptadosById(idPerro: Int): Adoptado?



}