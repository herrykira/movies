package com.example.kinhangpoon.movies.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinhangpoon.movies.model.service.MovieRepository
import com.example.kinhangpoon.movies.model.service.NetworkService
import com.example.kinhangpoon.movies.viewModel.MovieViewModel

val Injector: ViewModelFactoryProvider
    get() = currentInjector

@Volatile
private var currentInjector: ViewModelFactoryProvider = DefaultViewModelProvider

private object DefaultViewModelProvider : ViewModelFactoryProvider {
    private fun getMovieRepository(): MovieRepository {
        return MovieRepository.getInstance(NetworkService())
    }

    override fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory {
        val repository = getMovieRepository()
        return MovieViewModelFactory(repository)
    }
}

interface ViewModelFactoryProvider {
    fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory
}

/**
 * Factory for creating a [MovieViewModel] with a constructor that takes a [MovieRepository]
 */
class MovieViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MovieViewModel(repository) as T
}