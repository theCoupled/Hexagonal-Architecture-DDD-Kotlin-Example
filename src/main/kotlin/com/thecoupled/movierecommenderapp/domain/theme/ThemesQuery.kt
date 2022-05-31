package com.thecoupled.movierecommenderapp.domain.theme

data class ThemesQuery(
    val names: Set<ThemeName>? = null
)