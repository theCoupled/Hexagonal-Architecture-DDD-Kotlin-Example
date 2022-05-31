package com.thecoupled.movierecommenderapp.domain.user

data class UsersQuery(
    val emails: Set<UserEmail>? = null
)