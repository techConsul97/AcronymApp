package com.sebqv97.acronymapp.domain.repository

import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import kotlinx.coroutines.flow.Flow


interface IAcronymRepository {
    suspend fun getWordsFromDataSource(queryType:String,searchedWord:String): Flow<Resource<WordFormsItem>>
}