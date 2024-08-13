package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.utils.Movie

class MovieAdapter(
    private val movies: MutableList<Movie>,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMovieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val tvMoviePhoneNumber: TextView = itemView.findViewById(R.id.tvMoviePhoneNumber)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(movie: Movie) {
            tvMovieTitle.text = movie.title
            tvMoviePhoneNumber.text = movie.phoneNumber
            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

        holder.btnEdit.setOnClickListener {
            onEditClick(position)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount() = movies.size

    fun addMovie(movie: Movie) {
        movies.add(movie)
        notifyItemInserted(movies.size - 1)
    }

    fun updateMovie(position: Int, movie: Movie) {
        movies[position] = movie
        notifyItemChanged(position)
    }

    fun deleteMovie(position: Int) {
        movies.removeAt(position)
        notifyItemRemoved(position)
    }
}
