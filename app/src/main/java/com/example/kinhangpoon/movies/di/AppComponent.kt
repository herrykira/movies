package com.example.kinhangpoon.movies.di

import android.content.Context
import com.example.kinhangpoon.movies.model.service.MovieRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubComponent::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun detailComponent(): DetailComponent.Factory

    fun searchComponent(): SearchComponent.Factory

    fun movieRepository(): MovieRepository
}