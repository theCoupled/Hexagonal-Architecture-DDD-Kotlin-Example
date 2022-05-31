package com.thecoupled.movierecommenderapp.domain.user

interface UsersRepository {
    fun findAll(): List<User>
    fun find(id: UserId): User?
    fun save(user: User)
    fun query(query: UsersQuery): List<User>
    fun nextId(): UserId
}