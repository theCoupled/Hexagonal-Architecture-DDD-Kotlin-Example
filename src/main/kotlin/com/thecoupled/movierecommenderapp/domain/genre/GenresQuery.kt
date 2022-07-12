package com.thecoupled.movierecommenderapp.domain.genre

data class GenresQuery(
    val ids: Set<GenreId>? = null
)