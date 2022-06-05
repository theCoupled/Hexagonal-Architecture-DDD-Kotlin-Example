package com.thecoupled.movierecommenderapp.domain.genre

@JvmInline
value class GenreName private constructor(val value: String) {
    init {
        if (value.isBlank()) {
            throw GenreNameEmptyException()
        }
    }

    companion object {
        operator fun invoke(value: String): GenreName {
            return GenreName(value.lowercase().trim())
        }
    }
}