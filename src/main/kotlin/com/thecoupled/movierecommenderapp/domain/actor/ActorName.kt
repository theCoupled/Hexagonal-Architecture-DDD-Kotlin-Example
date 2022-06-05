package com.thecoupled.movierecommenderapp.domain.actor

@JvmInline
value class ActorName private constructor(val value: String) {
    init {
        if (value.isBlank()) {
            throw ActorNameEmptyException()
        }
    }

    companion object {
        operator fun invoke(value: String): ActorName {
            return ActorName(value.lowercase().trim())
        }
    }
}