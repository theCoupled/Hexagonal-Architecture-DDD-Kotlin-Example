package com.thecoupled.movierecommenderapp.application.like.create

import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.user.UserId

class CreateLikeCommand(
    val userId: UserId,
    val movieId: MovieId
)