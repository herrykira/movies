package com.example.kinhangpoon.movies.model.service

import com.example.kinhangpoon.movies.BuildConfig
import com.example.kinhangpoon.movies.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkService @Inject constructor(private val movieService: MovieSearchApi) {
    suspend fun getMoviesFlow(text: String, index: String): Flow<List<MovieResponse>?> = flow {
        val response = movieService.searchMovieByFlow(BuildConfig.ApiKey, text, index)
        if (response != null && response.isSuccessful) {
            val movieList = response.body()?.results
            emit(movieList)
        } else {
            emit(emptyList())
        }
    }
}