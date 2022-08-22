package com.sebqv97.acronymapp.domain.model

data class WordFormsItem(
    val shortForm:String,
    val longForms: List<WordLongForms>
)