package com.thecoupled.movierecommenderapp.domain.actor

data class ActorsQuery(
    val names: Set<ActorName>? = null
)