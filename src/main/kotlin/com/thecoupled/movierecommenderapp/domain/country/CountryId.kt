package com.thecoupled.movierecommenderapp.domain.country

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidUuIdError
import com.thecoupled.movierecommenderapp.domain.shared.isValidUuid
import java.util.*

@JvmInline
value class CountryId private constructor(private val value: UUID): AggregateId {
    companion object {
        fun createFromString(value: String): Either<InvalidUuIdError, CountryId> =
            when (isValidUuid(value)) {
                true -> CountryId(UUID.fromString(value)).right()
                else -> InvalidUuIdError(value).left()
            }
    }
}