package com.example.kinhangpoon.movies.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kinhangpoon.movies.MovieApplication
import com.example.kinhangpoon.movies.R
import com.example.kinhangpoon.movies.databinding.FragmentMovieDetailBinding
import com.example.kinhangpoon.movies.model.response.MovieResponse
import com.example.kinhangpoon.movies.ui.MovieSearchActivity.Companion.MOVIE_EXTRAS

class MovieDetailFragment : Fragment() {

    private lateinit var movie: MovieResponse
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val detailComponentFactory =
            (requireActivity().application as MovieApplication).appComponent.detailComponent()
        detailComponentFactory.create().inject(this)
    }

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
            movieTitle.text = resources.getString(R.string.movie_title) + movie.title
            movieOriginTitle.text =
                resources.getString(R.string.movie_original_title) + movie.originalTitle
            movieOverview.text = resources.getString(R.string.movie_overview) + movie.overview
            movieReleaseDate.text =
                resources.getString(R.string.movie_release_date) + movie.releaseDate
            moviePopularity.text = resources.getString(R.string.movie_popularity) + movie.popularity
            movieVoteCount.text = resources.getString(R.string.movie_vote_count) + movie.voteCount
            movieVoteAverage.text =
                resources.getString(R.string.movie_vote_average) + movie.voteAverage
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