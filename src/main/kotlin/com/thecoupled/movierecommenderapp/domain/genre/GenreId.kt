package com.thecoupled.movierecommenderapp.domain.genre

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.*

@JvmInline
value class GenreId(override val value: UUID) : AggregateId {
    companion object {
        fun createFromString(value : String): GenreId = GenreId(UUID.fromString(value))
    }
}