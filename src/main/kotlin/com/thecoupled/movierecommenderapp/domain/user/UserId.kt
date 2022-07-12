package com.thecoupled.movierecommenderapp.domain.user

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.*

@JvmInline
value class UserId(val value: UUID) : AggregateId