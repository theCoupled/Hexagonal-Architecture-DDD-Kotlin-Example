package com.thecoupled.movierecommenderapp.application.user.create

import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.user.User
import com.thecoupled.movierecommenderapp.domain.user.UserAlreadyExistingException
import com.thecoupled.movierecommenderapp.domain.user.UserEmail
import com.thecoupled.movierecommenderapp.domain.user.UserName
import com.thecoupled.movierecommenderapp.domain.user.UsersQuery
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import com.thecoupled.movierecommenderapp.domain.user.createNewUser

class UserCreator(private val usersRepository: UsersRepository) {

    fun create(name: UserName, email: UserEmail): Pair<User, List<DomainEvent>> {
        assertUserNotExisting(email)
        val user = createUser(email, name)
        usersRepository.save(user)

        return Pair(user, listOf())
    }

    private fun assertUserNotExisting(email: UserEmail) {
        if (usersRepository.query(UsersQuery(emails = setOf(email))).isNotEmpty()) {
            throw UserAlreadyExistingException()
        }
    }

    private fun createUser(email: UserEmail, name: UserName): User =
        createNewUser(
            usersRepository = usersRepository,
            name = name,
            email = email
        )
}