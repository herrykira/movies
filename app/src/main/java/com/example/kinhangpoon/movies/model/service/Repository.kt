package com.example.kinhangpoon.movies.model.service

import com.example.kinhangpoon.movies.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun searchQuery(text: String, index: String): Flow<List<MovieResponse>?>
}