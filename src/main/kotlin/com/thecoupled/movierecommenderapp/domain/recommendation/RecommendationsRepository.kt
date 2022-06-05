package com.thecoupled.movierecommenderapp.domain.recommendation

interface RecommendationsRepository {
    fun save(theme: Recommendation)
    fun findAll(): List<Recommendation>
    fun nextId(): RecommendationId
}