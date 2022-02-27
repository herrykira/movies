package com.example.kinhangpoon.movies.di

import com.example.kinhangpoon.movies.ui.MovieDetailFragment
import dagger.Subcomponent

@Subcomponent
interface DetailComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }

    fun inject(fragment: MovieDetailFragment)
}