package com.thecoupled.movierecommenderapp.domain.theme

import java.util.UUID

fun arbitraryTheme(
    id: ThemeId = ThemeId(UUID.randomUUID()),
    name: ThemeName = ThemeName("random name")
): Theme =
    Theme(
        id = id,
        name = name
    )