package com.thecoupled.movierecommenderapp.domain.director


@JvmInline
value class DirectorName private constructor(val value: String) {
    init {
        if (value.isBlank()) {
            throw DirectorNameCannotBeEmptyException()
        }
    }

    companion object {
        operator fun invoke(value: String): DirectorName {
            return DirectorName(value.lowercase().trim())
        }
    }
}