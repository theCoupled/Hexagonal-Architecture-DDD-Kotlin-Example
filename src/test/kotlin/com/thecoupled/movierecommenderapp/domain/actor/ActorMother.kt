package com.thecoupled.movierecommenderapp.domain.actor

import java.util.*

fun arbitraryActor(
    id: ActorId = ActorId(UUID.randomUUID()),
    name: ActorName = ActorName("random name")
): Actor =
    Actor(
        id = id,
        name = name
    )