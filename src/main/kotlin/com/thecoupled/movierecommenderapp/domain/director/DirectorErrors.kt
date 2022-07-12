package com.thecoupled.movierecommenderapp.domain.director

import com.thecoupled.movierecommenderapp.domain.shared.error.AggregateNotFoundError

data class DirectorNotFoundError(override val id: String): AggregateNotFoundError(id)