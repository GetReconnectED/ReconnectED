package com.getreconnected.reconnected.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun toList(value: String): List<String> = value.split(",")
}
