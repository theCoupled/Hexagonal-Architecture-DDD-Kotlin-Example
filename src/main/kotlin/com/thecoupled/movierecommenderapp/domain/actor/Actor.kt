package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class Actor(
    override val id: ActorId,
    val name: ActorName
) : Aggregate