package com.thecoupled.movierecommenderapp.domain.movie

import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.validNel
import com.thecoupled.movierecommenderapp.domain.movie.errors.MovieValidationError
import com.thecoupled.movierecommenderapp.domain.shared.error.DomainErrors

@JvmInline
value class MovieName private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Validated<DomainErrors, MovieName> =
            when {
                value.isBlank() -> MovieValidationError.MovieNameBlankError.invalidNel()
                else -> MovieName(value).validNel()
            }
    }
}