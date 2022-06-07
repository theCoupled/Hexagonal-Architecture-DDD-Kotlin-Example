package com.thecoupled.movierecommenderapp.application.like.create

import com.thecoupled.movierecommenderapp.application.shared.Handler
import com.thecoupled.movierecommenderapp.domain.like.LikeId
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.user.UserId
import java.util.*


class CreateLikeHandler(
    private val likeCreator: LikeCreator
) : Handler<CreateLikeCommand, LikeId> {
    override fun handle(command: CreateLikeCommand): LikeId =
        likeCreator.create(
            userId = UserId(UUID.fromString(command.userId)),
            movieId = MovieId(UUID.fromString(command.movieId))
        ).first.id
}