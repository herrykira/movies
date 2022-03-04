package com.example.kinhangpoon.movies.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinhangpoon.movies.MovieApplication
import com.example.kinhangpoon.movies.common.GUIUtil
import com.example.kinhangpoon.movies.databinding.FragmentMovieSearchBinding
import com.example.kinhangpoon.movies.di.ViewModelFactory
import com.example.kinhangpoon.movies.model.adapter.MovieAdapter
import com.example.kinhangpoon.movies.model.response.MovieResponse
import com.example.kinhangpoon.movies.storage.SharedPreferencesStorage
import com.example.kinhangpoon.movies.ui.MovieSearchActivity.Companion.QUERY_EXTRAS
import com.example.kinhangpoon.movies.viewModel.MovieViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MovieSearchFragment : Fragment() {

    interface MovieHost {
        fun selectMovie(movie: MovieResponse)
        fun setQueryText(text: String)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var sharedPreferencesStorage: SharedPreferencesStorage

    private var movieAdapter: MovieAdapter? = null
    private lateinit var movieList: MutableList<MovieResponse>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var host: MovieHost
    private var query: String? = null
    private var index = 1
    private var isScrolling = false
    private var currentItem = 0
    private var totalItem = 0
    private var scrollOutItem = 0

    private lateinit var binding: FragmentMovieSearchBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val searchComponentFactory =
            (requireActivity().application as MovieApplication).appComponent.searchComponent()
        searchComponentFactory.create().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
        host = context as MovieHost
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            query = bundle.getString(QUERY_EXTRAS)
        }
        movieList = mutableListOf()
        movieAdapter = MovieAdapter(movieList, object : MovieAdapter.SelectedMovieDelegate {
            override fun onMovieSelected(movie: MovieResponse) {
                host.selectMovie(movie)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        binding.apply {
            /**
             * check query text so that it can automatically set query when configuration changes
             */
            query?.let {
                searchText.setText(query)
            }
            searchText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    movieList.clear()
                }

            })
            submitButton.setOnClickListener {
                val text = searchText.text.toString()
                /**
                 * save query text so that it can save query text when screen orientation changes
                 */
                host.setQueryText(text)
                if (!text.isEmpty()) {
                    fetchData(text, index)
                }
                /**
                 * close keyboard
                 */
                GUIUtil.hideSoftKeyboard(root)
            }

            linearLayoutManager = LinearLayoutManager(context)
            recycleView.layoutManager = linearLayoutManager
            recycleView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            recycleView.adapter = movieAdapter
            /**
             * lazy loading
             */
            recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    currentItem = linearLayoutManager.childCount
                    totalItem = linearLayoutManager.itemCount
                    scrollOutItem = linearLayoutManager.findFirstVisibleItemPosition()

                    if (isScrolling && totalItem == currentItem + scrollOutItem) {
                        isScrolling = false
                        index++;
                        fetchData(searchText.text.toString(), index)
                    }
                }
            })

            lifecycleScope.launchWhenCreated {
                viewModel.searchState.collect {
                    when (it) {
                        is MovieViewModel.SearchUIState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is MovieViewModel.SearchUIState.Success -> {
                            progressBar.visibility = View.GONE
                            movieList.addAll(it.movies)
                            movieAdapter?.notifyDataSetChanged()
                        }
                        is MovieViewModel.SearchUIState.Idle -> {
                            progressBar.visibility = View.GONE
                        }
                        is MovieViewModel.SearchUIState.Failure -> {
                            progressBar.visibility = View.GONE
                            showToastMessage(it.message)
                            title.visibility = View.GONE

                        }
                        else -> Unit
                    }
                }
            }
        }
        return binding.root
    }

    private fun fetchData(text: String, index: Int) {
        /**
         * based on the requirement (1<index<=100), if index > 100, don't make a network call
         */
        if (index > 100) {
            showToastMessage("No Results")
            return;
        }
        viewModel.searchByQuery(text, "$index")
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        val BASE_URL = "https://api.themovies.org/3/search/"

        fun buildMovieSearchFragment(query: String): MovieSearchFragment {
            val movieSearchFragment = MovieSearchFragment()
            val bundle = Bundle()
            bundle.putString(QUERY_EXTRAS, query)
            movieSearchFragment.arguments = bundle
            return movieSearchFragment
        }
    }
}