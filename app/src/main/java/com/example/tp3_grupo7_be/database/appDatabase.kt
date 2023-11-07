package com.example.tp3_grupo7_be.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tp3_grupo7_be.models.Perro
import com.example.tp3_grupo7_be.models.StringListConverter

@Database(entities = [Perro::class], version = 8, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class appDatabase : RoomDatabase() {
    abstract fun perroDao(): perroDao

    companion object {

        var INSTANCE: appDatabase? = null

        fun getAppDataBase(context: Context): appDatabase? {
            if (INSTANCE == null) {
                synchronized(appDatabase::class) {
                    INSTANCE = Room.databaseBuilder(

                        context.applicationContext,
                        appDatabase::class.java,
                        "adopcionDB"

                    ).fallbackToDestructiveMigration().addMigrations().allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }

}