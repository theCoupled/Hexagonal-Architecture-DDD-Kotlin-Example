package com.thecoupled.movierecommenderapp.domain.movie

data class MoviesQuery(
    val ids: Set<MovieId>? = null,
    val names : Set<MovieName>? = null
)