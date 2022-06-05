package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import java.util.*

class InMemoryRecommendationsRepository(
    private val existing: Set<Recommendation> = setOf(),
    private val nextIds: MutableList<RecommendationId> = mutableListOf()
) : RecommendationsRepository, InMemoryRepository<Recommendation, RecommendationId>(existing, nextIds) {
    override fun nextId(): RecommendationId = nextIds.removeFirstOrNull() ?: RecommendationId(UUID.randomUUID())
}

fun createRecommendationsRepository(
    existingRecommendations: Set<Recommendation> = setOf(),
    nextIds: Set<RecommendationId> = setOf()
): RecommendationsRepository = InMemoryRecommendationsRepository(existingRecommendations, nextIds.toMutableList())