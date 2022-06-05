package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.movie.MovieName

data class RecommendationDslData(
    val movieName: MovieName,
    val score: RecommendationScore
)