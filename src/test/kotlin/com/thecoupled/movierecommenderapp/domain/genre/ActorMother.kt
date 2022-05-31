package com.thecoupled.movierecommenderapp.domain.genre

import java.util.*

fun arbitraryGenre(
    id: GenreId = GenreId(UUID.randomUUID()),
    name: GenreName = GenreName("random name")
): Genre =
    Genre(
        id = id,
        name = name
    )