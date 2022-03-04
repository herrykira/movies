package com.example.kinhangpoon.movies.di

import com.example.kinhangpoon.movies.model.service.MovieRepository
import com.example.kinhangpoon.movies.model.service.Repository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(movieRepository: MovieRepository): Repository
}