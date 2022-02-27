package com.example.kinhangpoon.movies.di

import com.example.kinhangpoon.movies.ui.MovieSearchFragment
import dagger.Subcomponent

@Subcomponent
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(fragment: MovieSearchFragment)
}