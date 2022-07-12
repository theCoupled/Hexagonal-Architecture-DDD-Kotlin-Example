package com.thecoupled.movierecommenderapp.domain.shared.error

open class AggregateNotFoundError(open val id: String) : InvalidInput()