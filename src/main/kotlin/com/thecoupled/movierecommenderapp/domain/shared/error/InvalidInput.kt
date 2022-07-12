package com.thecoupled.movierecommenderapp.domain.shared.error

import arrow.core.NonEmptyList

sealed interface DomainError

open class InvalidInput(val message: String = ""): DomainError

typealias DomainErrors = NonEmptyList<InvalidInput>