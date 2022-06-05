package com.thecoupled.movierecommenderapp.domain.like

import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.user.UserId
import java.time.Instant
import java.util.*

fun arbitraryLike(
    id : LikeId = LikeId(UUID.randomUUID()),
    movieId: MovieId = MovieId(UUID.randomUUID()),
    userId: UserId = UserId(UUID.randomUUID()),
    createdAt: Instant = Instant.now()
): Like =
    Like(
        id = id,
        userId = userId,
        movieId = movieId,
        createdAt = createdAt
    )