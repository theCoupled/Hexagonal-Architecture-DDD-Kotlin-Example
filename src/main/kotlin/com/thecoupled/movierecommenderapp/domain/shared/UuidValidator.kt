package com.thecoupled.movierecommenderapp.domain.shared

import java.util.*

fun isValidUuid(value: String): Boolean =
    runCatching {
        UUID.fromString(value)
        true
    }.getOrElse { false }