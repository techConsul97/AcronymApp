package com.sebqv97.acronymapp.domain.model

data class WordLongForms(
    val frequency: Int,
    val since: Int,
    val longForm: String,
    val wordVariants: List<WordVariants>
) {
    fun getLongForms(): String = longForm
    fun getCurrentLongForm(name: String): WordLongForms? {
        return if (name == longForm)
            this
        else null
    }

}
