package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class Recommendation(
    override val id: RecommendationId,
    val score: RecommendationScore,
    val movieId: MovieId,
    val info: RecommendationInfo
): Aggregate