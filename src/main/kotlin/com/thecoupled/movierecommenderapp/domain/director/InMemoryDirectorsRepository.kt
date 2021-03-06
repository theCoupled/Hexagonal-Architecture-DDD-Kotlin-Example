package com.thecoupled.movierecommenderapp.domain.director

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryDirectorsRepository(
    private val existing: Set<Director> = setOf(),
    private val nextIds: MutableList<DirectorId> = mutableListOf()
) : InMemoryRepository<Director, DirectorId>(existing, nextIds), DirectorsRepository {

    override fun query(query: DirectorsQuery): List<Director> {
        val returnList: MutableList<Director> = mutableListOf()

        if (query.ids != null) {
            returnList += collection.values.filter { entity -> query.ids.contains(entity.id) }
        }

        return returnList
    }

    override fun nextId(): DirectorId = nextIds.removeFirstOrNull() ?: DirectorId(UUID.randomUUID())
}

fun createDirectorsRepository(
    existingDirectors: Set<Director> = setOf(),
    nextIds: Set<DirectorId> = setOf()
): DirectorsRepository = InMemoryDirectorsRepository(existingDirectors, nextIds.toMutableList())