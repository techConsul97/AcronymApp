package com.sebqv97.acronymapp.data.remote.model_dto


import com.google.gson.annotations.SerializedName
import com.sebqv97.acronymapp.data.local.entities.WordFormsEntity
import com.sebqv97.acronymapp.domain.model.WordFormsItem

data class WordFormsItemDto(
    @SerializedName("lfs")
    val lfs: List<LfDto>,
    @SerializedName("sf")
    val sf: String
){
    fun toWordFormsItem():WordFormsItem = WordFormsItem(
        shortForm = sf,
        longForms = lfs.map { it.toWordLongForms() }
    )
    fun toWordEntity():WordFormsEntity = WordFormsEntity(
        shortForm = sf,
        longForms = lfs.map { it.toWordLongForms() }
    )
}