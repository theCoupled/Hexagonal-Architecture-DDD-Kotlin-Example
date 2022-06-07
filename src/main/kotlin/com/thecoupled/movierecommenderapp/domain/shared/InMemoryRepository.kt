package com.thecoupled.movierecommenderapp.domain.shared

open class InMemoryRepository<A : Aggregate, I : AggregateId>(
    private val existing: Set<A> = setOf(),
    private val nextIds: MutableList<I> = mutableListOf()
) {
    protected val collection: MutableMap<I, A> = mutableMapOf()

    init {
        existing.forEach { existingEntity -> collection[existingEntity.id as I] = existingEntity }
    }

    fun save(entity: A) {
        collection[entity.id as I] = entity
    }

    fun find(id: I): A? = collection.getOrDefault(id, null)
    fun findAll(): List<A> = collection.values.toList()
}