package com.thecoupled.movierecommenderapp.domain.actor

interface ActorsRepository {
    fun save(actor: Actor)
    fun findAll(): List<Actor>
    fun query(query: ActorsQuery): List<Actor>
    fun nextId(): ActorId
}