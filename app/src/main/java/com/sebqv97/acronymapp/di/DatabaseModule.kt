package com.sebqv97.acronymapp.di

import android.content.Context
import androidx.room.Room
import com.sebqv97.acronymapp.data.local.AcronymDao
import com.sebqv97.acronymapp.data.local.AcronymDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

        @Provides
        fun provideRoomInstance(
            @ApplicationContext context: Context
        ) = Room.databaseBuilder(
            context,
            AcronymDatabase::class.java,
            "acronym_db"
        ).build()

        @Provides
        fun provideDatabaseDao(database: AcronymDatabase ) = database.acronymDao()

}