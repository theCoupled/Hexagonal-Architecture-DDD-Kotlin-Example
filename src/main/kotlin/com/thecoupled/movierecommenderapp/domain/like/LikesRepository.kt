package com.thecoupled.movierecommenderapp.domain.like

interface LikesRepository {
    fun save(like: Like)
    fun findAll(): List<Like>
    fun find(id: LikeId): Like?
    fun query(query: LikesQuery): List<Like>
    fun nextId(): LikeId
}