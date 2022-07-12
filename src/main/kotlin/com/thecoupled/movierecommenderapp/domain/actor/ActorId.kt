package com.thecoupled.movierecommenderapp.domain.actor

import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.validNel
import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import com.thecoupled.movierecommenderapp.domain.shared.error.DomainErrors
import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidUuIdError
import com.thecoupled.movierecommenderapp.domain.shared.isValidUuid
import java.util.*

@JvmInline
value class ActorId(val value: UUID) : AggregateId {
    companion object {
        fun createFromString(value: String): Validated<DomainErrors, ActorId> =
            when (isValidUuid(value)) {
                true -> ActorId(UUID.fromString(value)).validNel()
                else -> InvalidUuIdError(value).invalidNel()
            }

    }
}