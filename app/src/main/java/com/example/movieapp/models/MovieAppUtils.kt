package com.example.movieapp.models

import android.content.Context
import android.content.SharedPreferences
import com.example.movieapp.utils.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieAppUtils(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MovieApp", Context.MODE_PRIVATE)


    fun saveMoviesToCache(movies: List<Movie>) {
        val jsonMovies = Gson().toJson(movies)

        val editor = sharedPreferences.edit()

        editor.putString("movies", jsonMovies)

        editor.apply()
    }

    fun loadMoviesFromCache(): MutableList<Movie> {
        val jsonMovies = sharedPreferences.getString("movies", null)

        return if (jsonMovies != null) {
            Gson().fromJson(jsonMovies, object : TypeToken<MutableList<Movie>>() {}.type)
        } else {
            mutableListOf()
        }
    }
}