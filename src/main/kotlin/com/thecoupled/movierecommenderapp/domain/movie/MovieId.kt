package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class MovieId(override val value: UUID): AggregateId