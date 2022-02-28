package com.example.kinhangpoon.movies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinhangpoon.movies.model.response.MovieResponse
import com.example.kinhangpoon.movies.model.service.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: Repository) :
    ViewModel() {

    private val _searchState = MutableStateFlow<SearchUIState>(SearchUIState.Idle)
    val searchState: StateFlow<SearchUIState> = _searchState


    fun searchByQuery(text: String, index: String) = viewModelScope.launch {
        _searchState.value = SearchUIState.Loading
        movieRepository.searchQuery(text, index)
            .catch { cause ->
                _searchState.value =
                    if (cause.message != null) SearchUIState.Failure(cause.message!!) else SearchUIState.Failure(
                        "Not Known"
                    )
            }
            .collect { movies ->
                if (movies == null || movies.isEmpty()) {
                    _searchState.value = SearchUIState.Failure("No results")
                } else {
                    _searchState.value = SearchUIState.Success(movies)
                }
            }
    }

    sealed class SearchUIState {
        object Idle : SearchUIState()
        data class Success(val movies: List<MovieResponse>) : SearchUIState()
        data class Failure(val message: String) : SearchUIState()
        object Loading : SearchUIState()
    }
}