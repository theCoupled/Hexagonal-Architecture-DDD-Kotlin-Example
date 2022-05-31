package com.thecoupled.movierecommenderapp.domain.genre

import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class Genre(
    override val id: GenreId,
    val name: GenreName
) : Aggregate