package com.example.kinhangpoon.movies.model.service

import android.util.Log
import com.example.kinhangpoon.movies.BuildConfig
import com.example.kinhangpoon.movies.model.response.MovieResponse
import com.example.kinhangpoon.movies.model.response.Response
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback

class MovieSearchRepository constructor(val movieSearchApi: MovieSearchApi?) {

    fun searchByQuery(text: String, index: String, movieSearchCallback: MovieSearchRepository.movieSearchCallback) {
        movieSearchApi?.searchMovie(BuildConfig.ApiKey, text, index)
            ?.enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if (response != null && response.isSuccessful()) {
                        val movies = response.body()?.results
                        if (movies != null) {
                            movieSearchCallback.onSuccess(movies)
                        }
                    } else {
                        movieSearchCallback.onFailure()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    movieSearchCallback.onFailure()
                }
            })
    }

    fun searchByQueryByRxJava(text: String, index: String, movieSearchCallback: movieSearchCallback){
        movieSearchApi?.searchMovieByRxJava(BuildConfig.ApiKey, text, index)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe {
//                if (it.results == null) movieSearchCallback.onFailure()
//                else{
//                    movieSearchCallback.onSuccess(it.results!!)
//                }
//            }
            ?.subscribeWith(object :Observer<Response> {
                var disposable: Disposable? = null
                override fun onComplete() {
                    Log.i("MovieSearchRepository", "complete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("MovieSearchRepository", "disposable")
                    this.disposable = d
                }

                override fun onNext(t: Response) {
                    if (t != null && t.results!=null) {
                        val movies = t.results
                        if (movies != null) {
                            movieSearchCallback.onSuccess(movies)
                        }
                    } else {
                        movieSearchCallback.onFailure()
                    }                  }

                override fun onError(e: Throwable) {
                    movieSearchCallback.onFailure()
                }

            })
    }

    fun searchByQueryByRxJavaSingle(text: String, index: String, movieSearchCallback: movieSearchCallback){
         val disposable = (movieSearchApi?.searchMovieByRxJavaSingle(BuildConfig.ApiKey, text, index)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<Response>(){
                override fun onSuccess(t: Response) {
                    if (t != null && t.results!=null) {
                        val movies = t.results
                        if (movies != null) {
                            movieSearchCallback.onSuccess(movies)
                        }
                    } else {
                        movieSearchCallback.onFailure()
                    }                }

                override fun onError(e: Throwable) {
                    movieSearchCallback.onFailure()
                }

            }))
    }

    interface movieSearchCallback {
        fun onSuccess(movies: List<MovieResponse>)
        fun onFailure()
    }
}
