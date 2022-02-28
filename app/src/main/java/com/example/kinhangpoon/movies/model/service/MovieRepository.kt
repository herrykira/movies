package com.example.kinhangpoon.movies.model.service

import com.example.kinhangpoon.movies.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: NetworkService) : Repository {
    override suspend fun searchQuery(text: String, index: String): Flow<List<MovieResponse>?> =
        movieService.getMoviesFlow(text, index)

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(movieService: NetworkService) = instance ?: synchronized(this) {
            instance ?: MovieRepository(movieService).also { instance = it }
        }
    }
}