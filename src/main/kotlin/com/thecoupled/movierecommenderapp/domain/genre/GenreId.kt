package com.thecoupled.movierecommenderapp.domain.genre

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class GenreId(override val value: UUID) : AggregateId