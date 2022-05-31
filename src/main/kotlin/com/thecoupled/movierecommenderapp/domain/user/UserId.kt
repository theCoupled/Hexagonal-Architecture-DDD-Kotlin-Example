package com.thecoupled.movierecommenderapp.domain.user

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class UserId(override val value: UUID) : AggregateId