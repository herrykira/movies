package com.example.kinhangpoon.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kinhangpoon.movies.databinding.FragmentMovieDetailBinding
import com.example.kinhangpoon.movies.model.response.MovieResponse
import com.example.kinhangpoon.movies.ui.MovieSearchActivity.Companion.MOVIE_EXTRAS

class MovieDetailFragment : Fragment() {

    private lateinit var movie: MovieResponse
    private val TITLE = "Title: "
    private val ORIGINAL_TITLE = "Original Title: "
    private val OVERVIEW = "Overview: "
    private val RELEASE_DATE = "Release Date: "
    private val VOTE_COUNT = "Vote Count: "
    private val VOTE_AVERAGE = "Vote Average: "
    private val POPULARITY = "Popularity: "
    private lateinit var binding: FragmentMovieDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE_EXTRAS)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding.apply {
            movieTitle.text = TITLE + movie.title
            movieOriginTitle.text = ORIGINAL_TITLE + movie.originalTitle
            movieOverview.text = OVERVIEW + movie.overview
            movieReleaseDate.text = RELEASE_DATE + movie.releaseDate
            moviePopularity.text = POPULARITY + movie.popularity
            movieVoteCount.text = VOTE_COUNT + movie.voteCount
            movieVoteAverage.text = VOTE_AVERAGE + movie.voteAverage
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MOVIE_EXTRAS, movie)
    }

    companion object {
        fun buildMovieDetailFragment(movie: MovieResponse): MovieDetailFragment {
            val movieDetailFragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_EXTRAS, movie)
            movieDetailFragment.arguments = bundle
            return movieDetailFragment
        }
    }

}