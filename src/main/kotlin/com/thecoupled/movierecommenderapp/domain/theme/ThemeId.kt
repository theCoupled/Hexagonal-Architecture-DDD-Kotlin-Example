package com.thecoupled.movierecommenderapp.domain.theme

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId
import java.util.*

@JvmInline
value class ThemeId(override val value: UUID) : AggregateId {
    companion object {
        fun createFromString(value : String): ThemeId = ThemeId(UUID.fromString(value))
    }
}