package com.example.movieapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieTitle = intent.getStringExtra("movieTitle")
        val moviePhoneNumber = intent.getStringExtra("moviePhoneNumber")

        findViewById<TextView>(R.id.tvMovieTitleDetail).text = movieTitle
        findViewById<TextView>(R.id.tvMoviePhoneNumberDetail).text = moviePhoneNumber
    }
}
