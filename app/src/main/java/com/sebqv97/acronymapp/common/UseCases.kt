package com.sebqv97.acronymapp.common

sealed class UseCases() {
   data class GetWords(val queryType:String, val searchedWord:String):UseCases()
}