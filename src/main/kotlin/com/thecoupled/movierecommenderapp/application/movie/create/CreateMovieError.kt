package com.thecoupled.movierecommenderapp.application.movie.create

sealed class CreateMovieError {
    object MissingThemes: CreateMovieError()
    object MissingDirectors: CreateMovieError()
    object MissingGenres: CreateMovieError()
}