package com.example.kinhangpoon.movies.model.service

import com.example.kinhangpoon.movies.model.response.Response
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchApi {
    @GET("movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Call<Response>

    @GET("movie")
    fun searchMovieByRxJava(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Observable<Response>

    @GET("movie")
    fun searchMovieByRxJavaSingle(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Single<Response>

    @GET("movie")
    suspend fun searchMovieByFlow(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): retrofit2.Response<Response>
}