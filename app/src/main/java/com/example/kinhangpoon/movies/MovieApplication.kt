package com.example.kinhangpoon.movies

import android.app.Application
import com.example.kinhangpoon.movies.di.AppComponent
import com.example.kinhangpoon.movies.di.DaggerAppComponent
import com.example.kinhangpoon.movies.storage.SharedPreferencesStorage

class MovieApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}