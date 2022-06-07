package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.shared.Aggregate
import com.thecoupled.movierecommenderapp.domain.user.UserId

data class Recommendation(
    override val id: RecommendationId,
    val userId: UserId,
    val existingMovieRecommendations: Set<MovieRecommendation>
) : Aggregate {

    val movieRecommendations: Set<MovieRecommendation>
        get() = existingMovieRecommendations.sortedBy { it.score.value }.toSet()

    val numMovieRecommendations: Int
        get() = movieRecommendations.size

    val hasEmptyMovieRecommendations: Boolean
        get() = existingMovieRecommendations.isEmpty()

    fun addRecommendation(
        movieId: MovieId,
        score: RecommendationScore,
        info: RecommendationInfo
    ): Recommendation =
        copy(
            existingMovieRecommendations = existingMovieRecommendations + MovieRecommendation(
                movieId = movieId,
                score = score,
                info = info
            )
        )
}

fun createNewRecommendation(recommendationsRepository: RecommendationsRepository, userId: UserId): Recommendation =
    Recommendation(
        id = recommendationsRepository.nextId(),
        userId = userId,
        existingMovieRecommendations = setOf()
    )

data class MovieRecommendation(
    val movieId: MovieId,
    val score: RecommendationScore,
    val info: RecommendationInfo
)