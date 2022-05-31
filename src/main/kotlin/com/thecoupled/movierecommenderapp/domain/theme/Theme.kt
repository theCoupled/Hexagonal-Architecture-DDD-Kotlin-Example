package com.thecoupled.movierecommenderapp.domain.theme

import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class Theme(
    override val id: ThemeId,
    val name: ThemeName
) : Aggregate