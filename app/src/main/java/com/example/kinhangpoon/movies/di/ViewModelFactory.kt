package com.example.kinhangpoon.movies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

//
/* Viewmodel object creation */
//val sampleViewmodel  = ViewModelProvider(this).get(MovieViewModel::class.java)

///* Viewmodel object creation with custom ViewModelFactory */
//val viewModelFactory = ViewModelFactory(MovieViewModel(/* can pass parameters now */))
//val sampleViewmodel  = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)

//class ViewModelFactory constructor(val viewModel: MovieViewModel) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        if (modelClass == MovieViewModel::class.java) {
//            viewModel as T
//        } else {
//            throw IllegalStateException("Unknown entity")
//        }
//}

//want to make ViewModelFactory generic.
// IntoMap API from dagger allows us to create a map of objects that can be injected into Android components

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}