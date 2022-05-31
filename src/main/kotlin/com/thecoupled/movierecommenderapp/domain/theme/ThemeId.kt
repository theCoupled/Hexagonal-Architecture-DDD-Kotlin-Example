package com.thecoupled.movierecommenderapp.domain.theme

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.UUID

@JvmInline
value class ThemeId(override val value: UUID) : AggregateId