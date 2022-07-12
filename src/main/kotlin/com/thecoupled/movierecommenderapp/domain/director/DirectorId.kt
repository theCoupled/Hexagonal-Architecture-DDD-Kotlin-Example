package com.thecoupled.movierecommenderapp.domain.director

import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.validNel
import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import com.thecoupled.movierecommenderapp.domain.shared.error.DomainErrors
import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidUuIdError
import com.thecoupled.movierecommenderapp.domain.shared.isValidUuid
import java.util.*

@JvmInline
value class DirectorId(val value: UUID) : AggregateId {
    override fun toString(): String = value.toString()

    companion object {
        fun createFromString(value: String): Validated<DomainErrors, DirectorId> =
            when (isValidUuid(value)) {
                true -> DirectorId(UUID.fromString(value)).validNel()
                else -> InvalidUuIdError(value).invalidNel()
            }
    }
}