package com.example.kinhangpoon.movies.di

import com.example.kinhangpoon.movies.model.service.MovieSearchApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideMovieSearchService(): MovieSearchApi {
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/search/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieSearchApi::class.java)
    }
}