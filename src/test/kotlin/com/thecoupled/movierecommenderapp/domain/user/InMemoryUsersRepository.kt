package com.thecoupled.movierecommenderapp.domain.user

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import java.util.UUID

class InMemoryUsersRepository(
    private val existing: Set<User> = setOf(),
    private val nextIds: MutableList<UserId> = mutableListOf()
) : InMemoryRepository<User, UserId>(existing, nextIds), UsersRepository {
    override fun query(query: UsersQuery): List<User> {
        val returnList: MutableList<User> = mutableListOf()

        if (query.emails != null) {
            returnList += collection.values.filter { entity -> query.emails!!.contains(entity.email) }
        }

        return returnList
    }

    override fun nextId(): UserId = nextIds.removeFirstOrNull() ?: UserId(UUID.randomUUID())
}

fun createUsersRepository(
    existingUsers: Set<User> = setOf(),
    nextIds: Set<UserId> = setOf()
): UsersRepository = InMemoryUsersRepository(existingUsers, nextIds.toMutableList())