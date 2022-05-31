package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class ActorId(override val value: UUID) : AggregateId