package com.thecoupled.movierecommenderapp.domain.like

import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.shared.Aggregate
import com.thecoupled.movierecommenderapp.domain.user.User
import com.thecoupled.movierecommenderapp.domain.user.UserId
import java.time.Clock
import java.time.Instant

data class Like(
    override val id: LikeId,
    val userId: UserId,
    val movieId: MovieId,
    val createdAt: Instant
) : Aggregate

fun createNewLike(
    likesRepository: LikesRepository,
    clock: Clock,
    user: User,
    movie: Movie
): Like =
    Like(
        id = likesRepository.nextId(),
        userId = user.id,
        movieId = movie.id,
        createdAt = Instant.now(clock)
    )