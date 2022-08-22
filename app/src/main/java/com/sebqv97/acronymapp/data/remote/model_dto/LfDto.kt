package com.sebqv97.acronymapp.data.remote.model_dto


import com.google.gson.annotations.SerializedName
import com.sebqv97.acronymapp.domain.model.WordLongForms

data class LfDto(
    @SerializedName("freq")
    val freq: Int,
    @SerializedName("lf")
    val lf: String,
    @SerializedName("since")
    val since: Int,
    @SerializedName("vars")
    val vars: List<VarDto>
){
    fun toWordLongForms():WordLongForms = WordLongForms(
        since = since,
        frequency = freq,
        longForm = lf,
        wordVariants = vars.map { it.toWordVariants() }
    )
}