package com.thecoupled.movierecommenderapp.application.like.create

import com.thecoupled.movierecommenderapp.domain.like.Like
import com.thecoupled.movierecommenderapp.domain.like.LikesRepository
import com.thecoupled.movierecommenderapp.domain.like.createNewLike
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieNotExistingException
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.user.User
import com.thecoupled.movierecommenderapp.domain.user.UserNotExistingException
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import java.time.Clock

class CreateLikeHandler(
    private val likesRepository: LikesRepository,
    private val usersRepository: UsersRepository,
    private val moviesRepository: MoviesRepository,
    private val clock: Clock
) {

    fun execute( command: CreateLikeCommand): Like {
        val like = createNewLike(
            likesRepository = likesRepository,
            clock = clock,
            user = command.findOrFailUser(),
            movie = command.findOrFailMovie()
        )

        likesRepository.save(like)
        return like
    }

    private fun CreateLikeCommand.findOrFailUser(): User =
        usersRepository.find(this.userId) ?: throw UserNotExistingException()

    private fun CreateLikeCommand.findOrFailMovie(): Movie =
        moviesRepository.find(this.movieId) ?: throw MovieNotExistingException()
}