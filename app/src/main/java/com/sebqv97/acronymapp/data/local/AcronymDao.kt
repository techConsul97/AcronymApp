package com.sebqv97.acronymapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sebqv97.acronymapp.data.local.entities.WordFormsEntity
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AcronymDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordForms(wordFormsEntity: WordFormsEntity)

    @Query("SELECT * FROM acronym_table WHERE shortForm LIKE :searchedWord" + " OR  longForms LIKE :searchedWord")
     fun getWordFromDb(searchedWord:String):WordFormsEntity
}