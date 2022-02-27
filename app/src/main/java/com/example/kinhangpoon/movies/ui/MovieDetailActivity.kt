package com.example.kinhangpoon.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kinhangpoon.movies.MovieSearchActivity.Companion.MOVIE_EXTRAS
import com.example.kinhangpoon.movies.databinding.ActivityMovieDetailBinding
import com.example.kinhangpoon.movies.model.response.MovieResponse
import com.example.kinhangpoon.movies.view.MovieDetailFragment

class MovieDetailActivity : AppCompatActivity() {
    private var movie: MovieResponse? = null
    private lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(MOVIE_EXTRAS)
        } else {
            movie = intent.getParcelableExtra(MOVIE_EXTRAS)
        }
        movie?.let { setupFragment(it) };
    }

    private fun setupFragment(movie: MovieResponse) {

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_container,
                MovieDetailFragment.buildMovieDetailFragment(movie),
                MovieDetailFragment::class.java.simpleName
            ).commit()
    }
    /**
     * save MovieResponse when screen orientation changes
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MOVIE_EXTRAS, movie)
    }
}
