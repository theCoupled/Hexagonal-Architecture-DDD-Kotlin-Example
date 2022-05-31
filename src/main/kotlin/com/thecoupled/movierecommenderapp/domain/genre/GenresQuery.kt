package com.thecoupled.movierecommenderapp.domain.genre

data class GenresQuery(
    val names: Set<GenreName>? = null
)