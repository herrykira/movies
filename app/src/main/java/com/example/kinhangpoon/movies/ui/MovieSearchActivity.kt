package com.example.kinhangpoon.movies.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kinhangpoon.movies.R
import com.example.kinhangpoon.movies.databinding.ActivityMovieSearchBinding
import com.example.kinhangpoon.movies.model.response.MovieResponse

/**
 * used MVP design pattern to develop this application
 * used retrofit to make api call to fetch Movie data
 * added progress dialog while making api call so that user can't do anything until call is finished
 * used RecycleView to show the lists of movie results
 * implemented lazy loading when scrolled to the bottom of the list and appended to the list
 * use a split screen for tablets
 * add unit test for MovieSearchPresenterImpl class and MovieSearchRepository class
 */
class MovieSearchActivity : AppCompatActivity(), MovieSearchFragment.MovieHost {

    companion object {
        const val MOVIE_EXTRAS = "movie extras"
        const val QUERY_EXTRAS = "query extras"
    }

    var query = ""
    private lateinit var binding: ActivityMovieSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY_EXTRAS) ?: ""
        }
        setupFragment(query);
    }

    /**
     * save query text when screen orientation changes
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_EXTRAS, query)
    }

    private fun setupFragment(query: String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_container,
                MovieSearchFragment.buildMovieSearchFragment(query),
                MovieSearchFragment::class.java.simpleName
            ).commit()
    }

    override fun selectMovie(movie: MovieResponse) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_EXTRAS, movie)
        /**
         * split screen for tablet
         */
        if (isTablet()) {
            intent.addFlags(
                Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT
                        or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }

        startActivity(intent)
    }

    /**
     * check if the device is tablet
     */
    private fun isTablet(): Boolean {
        val xlarge =
            resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK == 4
        val large =
            resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge || large
    }

    /**
     * update query text
     */
    override fun setQueryText(text: String) {
        query = text
    }
}
