package com.thecoupled.movierecommenderapp.domain.shared

import com.thecoupled.movierecommenderapp.domain.like.Like
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.recommendation.Recommendation
import com.thecoupled.movierecommenderapp.domain.user.User

data class UserCaseSetup(
    val user: User,
    val movies: Set<Movie> = setOf(),
    val likes: Set<Like> = setOf(),
    val expectedRecommendations: Set<Recommendation> = setOf()
)