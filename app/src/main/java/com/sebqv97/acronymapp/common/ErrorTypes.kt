package com.sebqv97.acronymapp.common

sealed class ErrorTypes(message: String){
    class EmptySearchField(message: String = "You left the Search Field Empty..."):ErrorTypes(message)
    class ProblematicHttpRequest(code: Int, message: String = "The Http Request failed with a code of $code"):ErrorTypes(message)
    class InternetConnectionFailed(message: String ="Couldn't reach server... Check your Internet Connection"):ErrorTypes(message)
    class EmptyQuery(message: String = "No values to be displayed..."):ErrorTypes(message)
    class ApiQueryTypeError(message: String = "Internal problem... Tell developer not to be one anymore =:)"):ErrorTypes(message)

}
