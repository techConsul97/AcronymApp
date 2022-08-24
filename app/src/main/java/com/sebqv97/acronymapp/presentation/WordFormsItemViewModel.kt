package com.sebqv97.acronymapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebqv97.acronymapp.common.UseCases
import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import com.sebqv97.acronymapp.domain.use_cases.get_words.Get_Words
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordFormsItemViewModel @Inject constructor(
    val getWordsUseCase:Get_Words
): ViewModel() {
    private val _liveWordsData:MutableStateFlow<Resource<WordFormsItem>> = MutableStateFlow(Resource.Loading<WordFormsItem>())
    val liveWordsData:StateFlow<Resource<WordFormsItem>> get() = _liveWordsData


    fun useCaseMapper(useCase:UseCases){
        when(useCase){
            is UseCases.GetWords -> getWordForms(useCase.queryType,useCase.searchedWord)

        }

    }

    //Request data
    fun getWordForms(queryType:String, searchedWord:String) = CoroutineScope(Dispatchers.IO).launch {
        getWordsUseCase(querytype = queryType, searchedWord = searchedWord).onEach {
            result -> _liveWordsData.value = result
            }.launchIn(viewModelScope)
        }
    }
