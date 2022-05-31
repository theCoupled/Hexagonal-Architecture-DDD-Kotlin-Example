package com.thecoupled.movierecommenderapp.domain.movie

@JvmInline
value class MovieName private constructor(val value: String) {
    init {
        if (value.isBlank()) {
            throw MovieNameCannotBeEmptyException()
        }
    }

    companion object {
        operator fun invoke(value: String): MovieName {
            return MovieName(value.lowercase().trim())
        }
    }
}