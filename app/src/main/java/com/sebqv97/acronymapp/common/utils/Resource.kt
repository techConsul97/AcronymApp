package com.sebqv97.acronymapp.common.utils

import com.sebqv97.acronymapp.common.ErrorTypes

sealed class Resource<T>(val data: T? = null, val errorType: ErrorTypes?) {
    class Success<T>(data: T) : Resource<T>(data,null)
    class Error<T>(errorType: ErrorTypes, data: T? = null) : Resource<T>(data,errorType)
    class Loading<T>(data: T? = null) : Resource<T>(data,null)
}