package com.thecoupled.movierecommenderapp.application.user.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.CommandHandler
import com.thecoupled.movierecommenderapp.domain.user.UserEmail
import com.thecoupled.movierecommenderapp.domain.user.UserId
import com.thecoupled.movierecommenderapp.domain.user.UserName

class CreateUserHandler(private val userCreator: UserCreator) : CommandHandler<CreateUserCommand, UserId> {
    override fun handle(command: CreateUserCommand): UserId =
        userCreator.create(
            name = UserName(command.name),
            email = UserEmail(command.email)
        ).first.id
}