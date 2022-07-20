package com.thecoupled.movierecommenderapp.domain.director

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.*

@JvmInline
value class DirectorId(override val value: UUID) : AggregateId {
    companion object {
        fun createFromString(value : String): DirectorId = DirectorId(UUID.fromString(value))
    }
}