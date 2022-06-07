package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.user.UserId
import java.util.*

fun arbitraryRecommendation(
    id: RecommendationId = RecommendationId(UUID.randomUUID()),
    movieRecommendations: Set<MovieRecommendation> = setOf(),
    userId: UserId = UserId(UUID.randomUUID())
): Recommendation =
    Recommendation(
        id = id,
        existingMovieRecommendations = movieRecommendations,
        userId = userId
    )

fun arbitraryMovieRecommendation(
    movieId: MovieId = MovieId(UUID.randomUUID()),
    score: RecommendationScore = RecommendationScore(1.4F),
    info: RecommendationInfo = RecommendationInfo(
        actorIds = setOf(),
        directorIds = setOf(),
        genreIds = setOf(),
        themeIds = setOf(),
        countryIds = setOf()
    )
): MovieRecommendation =
    MovieRecommendation(
        movieId = movieId,
        score = score,
        info = info
    )