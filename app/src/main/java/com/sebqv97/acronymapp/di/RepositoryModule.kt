package com.sebqv97.acronymapp.di

import com.sebqv97.acronymapp.data.local.AcronymDao
import com.sebqv97.acronymapp.data.remote.NactemApi
import com.sebqv97.acronymapp.data.repository.AcronymRepository
import com.sebqv97.acronymapp.domain.repository.IAcronymRepository
import com.sebqv97.acronymapp.domain.use_cases.get_words.Get_Words
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(dao: AcronymDao,api: NactemApi):IAcronymRepository = AcronymRepository(api,dao)

    @Provides
    fun provideWordsUseCase(repository: IAcronymRepository): Get_Words = Get_Words(repository)

}