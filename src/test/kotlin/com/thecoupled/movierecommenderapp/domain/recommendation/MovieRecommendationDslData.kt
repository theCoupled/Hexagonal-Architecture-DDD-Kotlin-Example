package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.movie.MovieName

data class MovieRecommendationDslData(
    val movieName: MovieName,
    val score: RecommendationScore
)