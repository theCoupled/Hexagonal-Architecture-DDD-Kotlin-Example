package com.thecoupled.movierecommenderapp.application.like.create


class CreateLikeHandler(
    private val likeCreator: LikeCreator
)  {
    /*
   override fun handle(command: CreateLikeCommand) {

       likeCreator.create(
           userId = UserId(UUID.fromString(command.userId)),
           movieId = MovieId(UUID.fromString(command.movieId))
       ).first.id


    }

     */
}