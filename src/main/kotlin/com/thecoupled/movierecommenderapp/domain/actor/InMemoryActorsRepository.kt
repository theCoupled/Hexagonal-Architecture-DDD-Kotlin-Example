package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryActorsRepository(
    private val existing: Set<Actor> = setOf(),
    private val nextIds: MutableList<ActorId> = mutableListOf()
) : InMemoryRepository<Actor, ActorId>(existing, nextIds), ActorsRepository {
    override fun query(query: ActorsQuery): List<Actor> {
        val returnList: MutableList<Actor> = mutableListOf()

        if (query.ids != null) {
            returnList += collection.values.filter { entity -> query.ids.contains(entity.id) }
        }

        return returnList
    }

    override fun nextId(): ActorId = nextIds.removeFirstOrNull() ?: ActorId(UUID.randomUUID())
}

fun createActorsRepository(
    existingActors: Set<Actor> = setOf(),
    nextIds: Set<ActorId> = setOf()
): ActorsRepository = InMemoryActorsRepository(existingActors, nextIds.toMutableList())