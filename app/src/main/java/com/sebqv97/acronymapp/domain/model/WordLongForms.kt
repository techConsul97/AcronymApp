package com.sebqv97.acronymapp.domain.model

data class WordLongForms(
    val frequency:Int,
    val since:Int,
    val longForm:String,
    val wordVariants: List<WordVariants>
)
