package com.thecoupled.movierecommenderapp.domain.like

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class LikeId(override val value: UUID) : AggregateId