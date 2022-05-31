package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import java.util.*

class InMemoryActorsRepository(
    private val existing: Set<Actor> = setOf(),
    private val nextIds: MutableList<ActorId> = mutableListOf()
) : InMemoryRepository<Actor, ActorId>(existing, nextIds), ActorsRepository {
    override fun query(query: ActorsQuery): List<Actor> {
        val returnList: MutableList<Actor> = mutableListOf()

        if (query.names != null) {
            returnList += collection.values.filter { entity -> query.names!!.contains(entity.name) }
        }

        return returnList
    }

    override fun nextId(): ActorId = nextIds.removeFirstOrNull() ?: ActorId(UUID.randomUUID())
}

fun createActorsRepository(
    existingActors: Set<Actor> = setOf(),
    nextIds: Set<ActorId> = setOf()
): ActorsRepository = InMemoryActorsRepository(existingActors, nextIds.toMutableList())