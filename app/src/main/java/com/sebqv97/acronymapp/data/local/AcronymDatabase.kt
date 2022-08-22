package com.sebqv97.acronymapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sebqv97.acronymapp.data.local.entities.WordFormsEntity

@Database(
    entities = [WordFormsEntity::class],
    version = 1,
    exportSchema = false

)
@TypeConverters(Converters::class)
abstract class AcronymDatabase:RoomDatabase() {

    abstract fun acronymDao():AcronymDao
}