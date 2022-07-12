package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.*

@JvmInline
value class ActorId(override val value: UUID) : AggregateId {
    companion object {
        fun createFromString(value : String): ActorId = ActorId(UUID.fromString(value))
    }
}