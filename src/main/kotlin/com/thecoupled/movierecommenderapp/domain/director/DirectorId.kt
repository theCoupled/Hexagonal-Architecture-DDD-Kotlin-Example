package com.thecoupled.movierecommenderapp.domain.director

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class DirectorId(override val value: UUID) : AggregateId