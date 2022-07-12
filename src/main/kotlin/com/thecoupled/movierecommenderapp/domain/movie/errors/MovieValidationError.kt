package com.thecoupled.movierecommenderapp.domain.movie.errors

import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidInput

sealed class MovieValidationError : InvalidInput() {
    object MovieNameBlankError: MovieValidationError()
    object MissingGenreIdError: MovieValidationError()
    object MissingThemeIdError: MovieValidationError()
    object MissingDirectorIdError: MovieValidationError()
}