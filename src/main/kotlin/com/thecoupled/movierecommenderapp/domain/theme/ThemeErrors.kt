package com.thecoupled.movierecommenderapp.domain.theme

import com.thecoupled.movierecommenderapp.domain.shared.error.AggregateNotFoundError

data class ThemeNotFoundError(override val id: String): AggregateNotFoundError(id)