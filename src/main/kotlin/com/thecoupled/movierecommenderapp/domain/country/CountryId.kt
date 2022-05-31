package com.thecoupled.movierecommenderapp.domain.country

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class CountryId(override val value: UUID): AggregateId