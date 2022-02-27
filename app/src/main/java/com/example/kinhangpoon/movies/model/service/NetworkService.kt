package com.example.kinhangpoon.movies.model.service

import com.example.kinhangpoon.movies.BuildConfig
import com.example.kinhangpoon.movies.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    suspend fun getMoviesFlow(text: String, index: String): Flow<List<MovieResponse>?> = flow {
        val response = movieService.searchMovieByFlow(BuildConfig.ApiKey, text, index)
        if (response != null && response.isSuccessful) {
            val movieList = response.body()?.results
            emit(movieList)
        } else {
            emit(emptyList())
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/search/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieSearchApi::class.java)
}