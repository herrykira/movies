package com.example.kinhangpoon.movies.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinhangpoon.movies.R
import com.example.kinhangpoon.movies.model.response.MovieResponse

class MovieAdapter(val movies: MutableList<MovieResponse>, val delegate: SelectedMovieDelegate) :
    RecyclerView.Adapter<MovieAdapter.MoiveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoiveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MoiveViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoiveViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleView.text = movie.title

        holder.itemView.setOnClickListener {
            delegate.onMovieSelected(movie)
        }
    }

    class MoiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView

        init {
            titleView = itemView.findViewById(R.id.title)
        }
    }

    interface SelectedMovieDelegate {
        fun onMovieSelected(
            movie: MovieResponse
        )
    }
}

