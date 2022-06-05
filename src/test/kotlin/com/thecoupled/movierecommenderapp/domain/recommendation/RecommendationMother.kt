package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import java.util.UUID

fun arbitraryRecommendation(
    id : RecommendationId = RecommendationId(UUID.randomUUID()),
    movieId: MovieId = MovieId(UUID.randomUUID()),
    score: RecommendationScore = RecommendationScore(1.4F),
    info: RecommendationInfo = RecommendationInfo(
        actorIds = setOf(),
        directorIds = setOf(),
        genreIds = setOf(),
        themeIds = setOf(),
        countryIds = setOf()
    )
): Recommendation =
    Recommendation(
        id = id,
        movieId = movieId,
        score = score,
        info = info
    )