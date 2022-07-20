package com.thecoupled.movierecommenderapp.domain.country

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.*

@JvmInline
value class CountryId(override val value: UUID): AggregateId {
    companion object {
        fun createFromString(value : String): CountryId = CountryId(UUID.fromString(value))
    }
}