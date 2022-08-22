package com.sebqv97.acronymapp.domain.use_cases.get_words

import com.sebqv97.acronymapp.common.ErrorTypes
import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import com.sebqv97.acronymapp.domain.repository.IAcronymRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class Get_Words @Inject constructor(
    val repository: IAcronymRepository
) {



            suspend operator fun invoke(querytype:String, searchedWord: String): Flow<Resource<WordFormsItem>> {
                if(searchedWord.isBlank()) {
                  return flow{
                      emit(Resource.Error(ErrorTypes.EmptySearchField()))
                  }
                }
                return repository.getWordsFromDataSource(queryType = querytype, searchedWord = searchedWord)
            }



}