package com.thecoupled.movierecommenderapp.domain.theme


data class ThemesQuery(
    val ids: Set<ThemeId>? = null,
    val names: Set<ThemeName>? = null
)