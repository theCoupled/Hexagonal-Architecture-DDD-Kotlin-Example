package com.thecoupled.movierecommenderapp.application.user.create

import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.user.User
import com.thecoupled.movierecommenderapp.domain.user.UserAlreadyExistingException
import com.thecoupled.movierecommenderapp.domain.user.UsersQuery
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import com.thecoupled.movierecommenderapp.domain.user.createNewUser

class CreateUserHandler(private val usersRepository: UsersRepository) {

    fun execute(command: CreateUserCommand): Pair<User, List<DomainEvent>> {
        command.assertUserNotExisting()
        val user = command.createUser()
        usersRepository.save(user)

        return Pair(user, listOf())
    }

    private fun CreateUserCommand.assertUserNotExisting() {
        if (usersRepository.query(UsersQuery(emails = setOf(this.email))).isNotEmpty()) {
            throw UserAlreadyExistingException()
        }
    }

    private fun CreateUserCommand.createUser(): User =
        createNewUser(
            usersRepository = usersRepository,
            name = this.name,
            email = this.email
        )
}