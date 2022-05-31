package com.thecoupled.movierecommenderapp.domain.director

import java.util.*

fun arbitraryDirector(
    id: DirectorId = DirectorId(UUID.randomUUID()),
    name: DirectorName = DirectorName("random name")
): Director =
    Director(
        id = id,
        name = name
    )