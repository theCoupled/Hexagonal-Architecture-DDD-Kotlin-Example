package com.thecoupled.movierecommenderapp.application.like.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.Command

class CreateLikeCommand(
    val userId: String,
    val movieId: String
) : Command