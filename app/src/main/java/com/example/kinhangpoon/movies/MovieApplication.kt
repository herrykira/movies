package com.example.kinhangpoon.movies

import android.app.Application
import com.example.kinhangpoon.movies.storage.SharedPreferencesStorage

class MovieApplication : Application() {
    open val sharedPreferences by lazy {
        SharedPreferencesStorage(this)
    }
}