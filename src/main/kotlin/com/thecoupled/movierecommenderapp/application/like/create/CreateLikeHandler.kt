package com.thecoupled.movierecommenderapp.application.like.create

import com.thecoupled.movierecommenderapp.domain.like.Like
import com.thecoupled.movierecommenderapp.domain.like.LikesRepository
import com.thecoupled.movierecommenderapp.domain.like.createNewLike
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieNotFoundException
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.user.User
import com.thecoupled.movierecommenderapp.domain.user.UserNotFoundException
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import java.time.Clock

class CreateLikeHandler(
    private val likesRepository: LikesRepository,
    private val usersRepository: UsersRepository,
    private val moviesRepository: MoviesRepository,
    private val clock: Clock
) {

    fun execute( command: CreateLikeCommand): Pair<Like, List<DomainEvent>> {
        val createdLike = command.createNewLike()
        likesRepository.save(createdLike)

        return Pair(createdLike, listOf())
    }

    private fun CreateLikeCommand.createNewLike(): Like =
        createNewLike(
            likesRepository = likesRepository,
            clock = clock,
            user = this.findOrFailUser(),
            movie = this.findOrFailMovie()
        )

    private fun CreateLikeCommand.findOrFailUser(): User =
        usersRepository.find(this.userId) ?: throw UserNotFoundException()

    private fun CreateLikeCommand.findOrFailMovie(): Movie =
        moviesRepository.find(this.movieId) ?: throw MovieNotFoundException()
}