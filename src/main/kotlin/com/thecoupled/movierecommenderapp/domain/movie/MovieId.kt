package com.thecoupled.movierecommenderapp.domain.movie

import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.validNel
import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import com.thecoupled.movierecommenderapp.domain.shared.error.DomainErrors
import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidUuIdError
import com.thecoupled.movierecommenderapp.domain.shared.isValidUuid
import java.util.*

@JvmInline
value class MovieId private constructor(val value: UUID): AggregateId {
    companion object {
        fun createFromString(id: String): Validated<DomainErrors, MovieId> =
            when(isValidUuid(id)) {
                true -> MovieId(UUID.fromString(id)).validNel()
                else -> InvalidUuIdError(id).invalidNel()
            }
    }
}