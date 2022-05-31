package com.thecoupled.movierecommenderapp.domain.user

import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class User(
    override val id: UserId,
    val name: UserName,
    val email: UserEmail
) : Aggregate

fun createNewUser(
    usersRepository: UsersRepository,
    name: UserName,
    email:UserEmail
): User = User(
    id = usersRepository.nextId(),
    name = name,
    email = email
)
