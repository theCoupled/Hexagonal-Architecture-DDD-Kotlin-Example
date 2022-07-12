package com.thecoupled.movierecommenderapp.domain.genre

import com.thecoupled.movierecommenderapp.domain.shared.error.AggregateNotFoundError

data class GenreNotFoundError(override val id: String): AggregateNotFoundError(id)