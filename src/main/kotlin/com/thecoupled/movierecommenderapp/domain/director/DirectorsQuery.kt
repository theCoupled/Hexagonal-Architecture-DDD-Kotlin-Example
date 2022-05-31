package com.thecoupled.movierecommenderapp.domain.director

data class DirectorsQuery(
    val names: Set<DirectorName>? = null
)