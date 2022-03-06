package com.example.kinhangpoon.movies.di

import com.example.kinhangpoon.movies.model.service.MovieRepository
import com.example.kinhangpoon.movies.model.service.MovieSearchApi
import com.example.kinhangpoon.movies.model.service.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMovieRepository(movieSearchApi: MovieSearchApi): Repository =
        MovieRepository(movieSearchApi)
}