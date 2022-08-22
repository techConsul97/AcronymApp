package com.sebqv97.acronymapp.data.remote

import com.sebqv97.acronymapp.data.remote.model_dto.WordFormsItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NactemApi {

    @GET("software/acromine/dictionary.py")
    suspend fun getWordFromApi(
        @Query("lf")providedLongForm:String?=null,
        @Query("sf")providedShortForm:String?=null
    ): Response<List<WordFormsItemDto>>
}