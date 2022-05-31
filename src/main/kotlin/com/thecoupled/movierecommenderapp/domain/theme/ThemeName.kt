package com.thecoupled.movierecommenderapp.domain.theme

@JvmInline
value class ThemeName private constructor(val value: String) {
    init {
        if (value.isBlank()) {
            throw ThemeNameCannotBeEmptyException()
        }
    }

    companion object {
        operator fun invoke(value: String): ThemeName {
            return ThemeName(value.lowercase().trim())
        }
    }
}