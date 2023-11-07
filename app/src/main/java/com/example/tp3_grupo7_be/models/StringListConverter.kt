package com.example.tp3_grupo7_be.models

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromStringList(value: String): MutableList<String> {
        return value.split(",").toMutableList()
    }

    @TypeConverter
    fun toStringList(value: MutableList<String>): String {
        return value.joinToString(",")
    }
}