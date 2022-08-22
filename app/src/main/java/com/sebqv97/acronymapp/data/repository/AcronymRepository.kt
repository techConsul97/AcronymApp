package com.sebqv97.acronymapp.data.repository

import android.util.Log
import com.sebqv97.acronymapp.common.Constants
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
                val apiResponse = when(queryType){
                    Constants.API_GET_ABBREVIATION -> api.getWordFromApi(providedLongForm = searchedWord)
                    Constants.API_GET_LONG_FORM -> api.getWordFromApi(providedShortForm = searchedWord)
                    else -> emit(Resource.Error("There is a problem with calling the API"))
                }as Response<List<WordFormsItemDto>>
                if(apiResponse.isSuccessful){
                    apiResponse.body().let {
                        //insert the new data to dataBase
                        val responseEntity = it!![0].toWordEntity()

                        dao.insertWordForms(WordFormsEntity("ss", listOf()))
                        dao.insertWordForms(WordFormsEntity("sf", listOf()))
                        dao.insertWordForms(WordFormsEntity("s3rwer", listOf()))
                        dao.insertWordForms(WordFormsEntity("dfs", listOf()))
                        dao.insertWordForms(responseEntity)
                    }

                    //after insert, as we want to stay with the Single-Source-Trush principle, look again in the db and transmit it
                     wordFromDb = dao.getWordFromDb(searchedWord)
                    if(wordFromDb == null)
                        //emit(Resource.Error("The insert of the word worked, but the retrieving from DB failed"))
                      //  WorkAROUND
                        emit(Resource.Success(apiResponse.body()!![0].toWordFormsItem()))
                    else{
                        emit(Resource.Success(wordFromDb.let{it!!.toWordFormsItem()}))
                        wordFromDb = null
                    }

                }

            }catch (e:HttpException){
                emit(Resource.Error(e.localizedMessage ?: "Problem regarding your HttpRequest"))
            }catch (e:IOException){
                emit(Resource.Error(message = "Couldn't reach the server... Check your Internet Connection"))
            }
        }


    }




}