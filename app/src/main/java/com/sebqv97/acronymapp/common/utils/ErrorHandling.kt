package com.sebqv97.acronymapp.common.utils

import com.sebqv97.acronymapp.common.ErrorTypes

fun getWordsUseCaseErrorHandler(errorType: ErrorTypes):String{
        //check which type of error was triggered
    return when(errorType){
        is ErrorTypes.ApiQueryTypeError -> errorType.message
        is ErrorTypes.EmptyQuery -> errorType.message
        is ErrorTypes.ProblematicHttpRequest-> errorType.message
        is ErrorTypes.EmptySearchField-> errorType.message
        is ErrorTypes.DBInsertionSuccessRetrievingFailed -> errorType.message
        else -> "Unexpected Error"
    }
}