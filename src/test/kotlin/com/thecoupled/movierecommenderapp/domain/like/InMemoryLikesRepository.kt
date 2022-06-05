package com.thecoupled.movierecommenderapp.domain.like

import com.thecoupled.movierecommenderapp.domain.genre.Genre
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.genre.GenresRepository
import com.thecoupled.movierecommenderapp.domain.genre.InMemoryGenresRepository
import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import java.util.*

class InMemoryLikesRepository(
    private val existing: Set<Like> = setOf(),
    private val nextIds: MutableList<LikeId> = mutableListOf()
) : LikesRepository, InMemoryRepository<Like, LikeId>(existing, nextIds) {
    override fun query(query: LikesQuery): List<Like> {
        val returnList: MutableList<Like> = mutableListOf()

        if (query.userIds != null) {
            returnList += collection.values.filter { entity -> query.userIds!!.contains(entity.userId) }
        }

        return returnList
    }

    override fun nextId(): LikeId = nextIds.removeFirstOrNull() ?: LikeId(UUID.randomUUID())
}

fun createLikesRepository(
    existingLikes: Set<Like> = setOf(),
    nextIds: Set<LikeId> = setOf()
): LikesRepository = InMemoryLikesRepository(existingLikes, nextIds.toMutableList())