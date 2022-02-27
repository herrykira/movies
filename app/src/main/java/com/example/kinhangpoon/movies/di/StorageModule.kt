package com.example.kinhangpoon.movies.di

import com.example.kinhangpoon.movies.storage.SharedPreferencesStorage
import com.example.kinhangpoon.movies.storage.Storage
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {
    @Binds
    abstract fun provideSharedPreferencesStorage(impl: SharedPreferencesStorage): Storage
}