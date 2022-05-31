package com.thecoupled.movierecommenderapp.application.user.create

import com.thecoupled.movierecommenderapp.domain.user.UserEmail
import com.thecoupled.movierecommenderapp.domain.user.UserName

class CreateUserCommand(
    val name: UserName,
    val email: UserEmail
)