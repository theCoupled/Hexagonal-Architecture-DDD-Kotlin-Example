package com.thecoupled.movierecommenderapp.domain.country


@JvmInline
value class CountryName private constructor(val value: String) {
    init {
        if (value.isBlank()) {
            throw CountryNameCannotBeEmptyException()
        }
    }

    companion object {
        operator fun invoke(value: String): CountryName {
            return CountryName(value.lowercase().trim())
        }
    }
}