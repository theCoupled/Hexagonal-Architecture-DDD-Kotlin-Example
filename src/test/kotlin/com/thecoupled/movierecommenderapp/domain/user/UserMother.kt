package com.thecoupled.movierecommenderapp.domain.user

import java.util.UUID

fun arbitraryUser(
    id: UserId = UserId(UUID.randomUUID()),
    name: UserName = UserName("random name"),
    email: UserEmail = UserEmail("random@email.com")
): User =
    User(
        id = id,
        name = name,
        email = email
    )