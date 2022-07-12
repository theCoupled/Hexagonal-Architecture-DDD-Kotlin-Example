package com.thecoupled.movierecommenderapp.domain.genre

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository

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

        if (query.names != null) {
            returnList += collection.values.filter { entity -> query.names.contains(entity.name) }
        }

        return returnList
    }

}

fun createGenresRepository(
    existingGenres: Set<Genre> = setOf(),
    nextIds: Set<GenreId> = setOf()
): GenresRepository = InMemoryGenresRepository(existingGenres, nextIds.toMutableList())