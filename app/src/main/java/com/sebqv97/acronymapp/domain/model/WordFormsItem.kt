package com.sebqv97.acronymapp.domain.model

import com.sebqv97.acronymapp.presentation.models.OverviewPageModel

data class WordFormsItem(
    val shortForm:String,
    val longForms: List<WordLongForms>
){
    fun toOverviewPageModel() : OverviewPageModel = OverviewPageModel(
        word = shortForm,
        longForms = longForms.map{it.getLongForms()}
    )
}