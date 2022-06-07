package com.thecoupled.movierecommenderapp.application.user.create

import com.thecoupled.movierecommenderapp.application.shared.Command

class CreateUserCommand(
    val name: String,
    val email: String
) : Command