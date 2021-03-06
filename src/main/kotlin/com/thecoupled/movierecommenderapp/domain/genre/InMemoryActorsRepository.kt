package com.thecoupled.movierecommenderapp.domain.genre

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryGenresRepository(
    private val existing: Set<Genre> = setOf(),
    private val nextIds: MutableList<GenreId> = mutableListOf()
) : InMemoryRepository<Genre, GenreId>(existing, nextIds), GenresRepository {
    override fun query(query: GenresQuery): List<Genre> {
        val returnList: MutableList<Genre> = mutableListOf()

        if (query.ids != null) {
            returnList += collection.values.filter { entity -> query.ids.contains(entity.id) }
        }

        return returnList
    }

    override fun nextId(): GenreId = nextIds.removeFirstOrNull() ?: GenreId(UUID.randomUUID())
}

fun createGenresRepository(
    existingGenres: Set<Genre> = setOf(),
    nextIds: Set<GenreId> = setOf()
): GenresRepository = InMemoryGenresRepository(existingGenres, nextIds.toMutableList())