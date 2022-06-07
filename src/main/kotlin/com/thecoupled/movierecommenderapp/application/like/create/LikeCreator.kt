package com.thecoupled.movierecommenderapp.application.like.create

import com.thecoupled.movierecommenderapp.domain.like.Like
import com.thecoupled.movierecommenderapp.domain.like.LikesRepository
import com.thecoupled.movierecommenderapp.domain.like.createNewLike
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.movie.MovieNotFoundException
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.user.User
import com.thecoupled.movierecommenderapp.domain.user.UserId
import com.thecoupled.movierecommenderapp.domain.user.UserNotFoundException
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import java.time.Clock

class LikeCreator(
    private val likesRepository: LikesRepository,
    private val usersRepository: UsersRepository,
    private val moviesRepository: MoviesRepository,
    private val clock: Clock
) {

    fun create(movieId: MovieId, userId: UserId): Pair<Like, List<DomainEvent>> {
        val createdLike = createNewLike(movieId = movieId, userId = userId)
        likesRepository.save(createdLike)

        return Pair(createdLike, listOf())
    }

    private fun createNewLike(movieId: MovieId, userId: UserId): Like =
        createNewLike(
            likesRepository = likesRepository,
            clock = clock,
            user = findOrFailUser(userId),
            movie = findOrFailMovie(movieId)
        )

    private fun findOrFailUser(userId: UserId): User =
        usersRepository.find(userId) ?: throw UserNotFoundException()

    private fun findOrFailMovie(movieId: MovieId): Movie =
        moviesRepository.find(movieId) ?: throw MovieNotFoundException()
}