package com.thecoupled.movierecommenderapp.domain.shared

import java.util.UUID

interface AggregateId {
    val value: UUID
}