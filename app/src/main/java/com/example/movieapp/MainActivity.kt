package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.models.MovieAppUtils
import com.example.movieapp.utils.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private var movies: MutableList<Movie> = mutableListOf()

    private lateinit var movieAppUtils: MovieAppUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieAppUtils = MovieAppUtils(this)

        movies = movieAppUtils.loadMoviesFromCache()

        movieAdapter = MovieAdapter(movies, this::onEditClick, this::onDeleteClick, this::onItemClick)
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        findViewById<Button>(R.id.btnAddMovie).setOnClickListener {
            val title = findViewById<EditText>(R.id.tvMovieTitle).text.toString()
            val phoneNumber = findViewById<EditText>(R.id.tvMoviePhoneNumber).text.toString()

            if (title.isNotEmpty() && phoneNumber.isNotEmpty()) {
                val movie = Movie(title, phoneNumber)
                movieAdapter.addMovie(movie)
                movieAppUtils.saveMoviesToCache(movies)
                findViewById<EditText>(R.id.tvMovieTitle).text.clear()
                findViewById<EditText>(R.id.tvMoviePhoneNumber).text.clear()
            }
        }
    }

    @SuppressLint("WrongViewCast")
    private fun onEditClick(position: Int) {
        val movie = movies[position]
        val etMovieTitle = findViewById<EditText>(R.id.tvMovieTitle)
        val etMoviePhoneNumber = findViewById<EditText>(R.id.tvMoviePhoneNumber)
        val btnAddMovie = findViewById<Button>(R.id.btnAddMovie)

        etMovieTitle.setText(movie.title)
        etMoviePhoneNumber.setText(movie.phoneNumber)
        btnAddMovie.text = "Save Edit"

        btnAddMovie.setOnClickListener {
            val updatedMovie = Movie(etMovieTitle.text.toString(), etMoviePhoneNumber.text.toString())
            movieAdapter.updateMovie(position, updatedMovie)
            movieAppUtils.saveMoviesToCache(movies)
            btnAddMovie.text = "Add Movie"
            btnAddMovie.setOnClickListener {
                val newTitle = etMovieTitle.text.toString()
                val newPhoneNumber = etMoviePhoneNumber.text.toString()
                if (newTitle.isNotEmpty() && newPhoneNumber.isNotEmpty()) {
                    val newMovie = Movie(newTitle, newPhoneNumber)
                    movieAdapter.addMovie(newMovie)
                    movieAppUtils.saveMoviesToCache(movies)
                    etMovieTitle.text.clear()
                    etMoviePhoneNumber.text.clear()
                }
            }
            etMovieTitle.text.clear()
            etMoviePhoneNumber.text.clear()
        }
    }

    private fun onDeleteClick(position: Int) {
        movieAdapter.deleteMovie(position)
        movieAppUtils.saveMoviesToCache(movies)
    }

    private fun onItemClick(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieTitle", movie.title)
        intent.putExtra("moviePhoneNumber", movie.phoneNumber)
        startActivity(intent)
    }
}

