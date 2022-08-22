package com.sebqv97.acronymapp.data.repository

import android.util.Log
import com.sebqv97.acronymapp.common.Constants
import com.sebqv97.acronymapp.common.ErrorTypes
import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.data.local.AcronymDao
import com.sebqv97.acronymapp.data.local.entities.WordFormsEntity
import com.sebqv97.acronymapp.data.remote.NactemApi
import com.sebqv97.acronymapp.data.remote.model_dto.WordFormsItemDto
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import com.sebqv97.acronymapp.domain.repository.IAcronymRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AcronymRepository @Inject constructor(
    val api:NactemApi,
    val dao:AcronymDao
) : IAcronymRepository {
     var wordFromDb:WordFormsEntity? = null
    override suspend fun getWordsFromDataSource(queryType:String,searchedWord:String): Flow<Resource<WordFormsItem>> = flow{
        emit(Resource.Loading())
        //try and get it from the database
        wordFromDb = dao.getWordFromDb(searchedWord)
         Log.d("WordFromDb",wordFromDb.toString())
        if(wordFromDb != null){
            emit(Resource.Success(data = wordFromDb!!.toWordFormsItem()))
        }else{
            try {
                val responseEntity:WordFormsEntity
                val apiResponse = when(queryType){
                    Constants.API_GET_ABBREVIATION -> api.getWordFromApi(providedLongForm = searchedWord)
                    Constants.API_GET_LONG_FORM -> api.getWordFromApi(providedShortForm = searchedWord)
                    else -> emit(Resource.Error(ErrorTypes.ApiQueryTypeError()))
                }as Response<List<WordFormsItemDto>>
                if(apiResponse.isSuccessful){
                    if(apiResponse.body().isNullOrEmpty()) emit(Resource.Error(ErrorTypes.EmptyQuery()))
                    else {
                        apiResponse.body().let {
                            //insert the new data to dataBase
                            responseEntity = it!![0].toWordEntity()
                            dao.insertWordForms(responseEntity)
                        }

                        //after insert, as we want to stay with the Single-Source-Trush principle, look again in the db and transmit it
                        wordFromDb = dao.getWordFromDb(responseEntity.shortForm)
                        if (wordFromDb == null)
                        //   emit(Resource.Error("The insert of the word worked, but the retrieving from DB failed"))
                        else {
                            emit(Resource.Success(wordFromDb.let { it!!.toWordFormsItem() }))
                            wordFromDb = null
                        }
                    }

                }

            }catch (e:HttpException){
                val errorCode = e.code()
                emit(Resource.Error(ErrorTypes.ProblematicHttpRequest(errorCode)))
            }catch (e:IOException){
                emit(Resource.Error(ErrorTypes.InternetConnectionFailed()))
            }
        }
    }




}