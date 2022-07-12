package com.thecoupled.movierecommenderapp.domain.actor

@JvmInline
value class ActorName private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): ActorName {
            return ActorName(value.lowercase().trim())
        }
    }
}