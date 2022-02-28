package com.example.kinhangpoon.movies.di

import android.content.Context
import com.example.kinhangpoon.movies.storage.SharedPreferencesStorage
import com.example.kinhangpoon.movies.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class StorageModule {
    @Binds
    abstract fun provideSharedPreferences(impl: SharedPreferencesStorage): Storage

    @Binds
    abstract fun provideContext(@ApplicationContext appContext: Context): Context
}