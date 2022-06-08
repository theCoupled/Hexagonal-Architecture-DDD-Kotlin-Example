package com.thecoupled.movierecommenderapp.application.user.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.Command

class CreateUserCommand(
    val name: String,
    val email: String
) : Command