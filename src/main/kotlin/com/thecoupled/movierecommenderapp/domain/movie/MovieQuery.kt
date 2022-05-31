package com.thecoupled.movierecommenderapp.domain.movie

data class MovieQuery(
    val names : Set<MovieName>? = null
)