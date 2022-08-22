package com.sebqv97.acronymapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import com.sebqv97.acronymapp.domain.model.WordLongForms

@Entity(tableName = "acronym_table")
data class WordFormsEntity(
    @PrimaryKey(autoGenerate = false)
    val shortForm:String,
    val longForms:List<WordLongForms>
){
    fun toWordFormsItem():WordFormsItem = WordFormsItem(
        shortForm = shortForm,
        longForms = longForms
    )
}
