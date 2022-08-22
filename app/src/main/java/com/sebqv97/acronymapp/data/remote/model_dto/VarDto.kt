package com.sebqv97.acronymapp.data.remote.model_dto


import com.google.gson.annotations.SerializedName
import com.sebqv97.acronymapp.domain.model.WordVariants

data class VarDto(
    @SerializedName("freq")
    val freq: Int,
    @SerializedName("lf")
    val lf: String,
    @SerializedName("since")
    val since: Int
){
    fun toWordVariants():WordVariants = WordVariants(
        since = since,
        frequency = freq,
        longForm = lf
    )
}