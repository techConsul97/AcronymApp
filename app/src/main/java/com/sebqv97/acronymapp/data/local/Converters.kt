package com.sebqv97.acronymapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sebqv97.acronymapp.domain.model.WordLongForms

class Converters {

val gson = Gson()
    @TypeConverter
    fun longToString(longForm: List<WordLongForms>): String = gson.toJson(longForm)

    @TypeConverter
    fun stringToLong(data: String): List<WordLongForms>{
        val listType = object : TypeToken<List<WordLongForms>>() {}.type
        return gson.fromJson(data, listType)
}
    }