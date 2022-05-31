package com.thecoupled.movierecommenderapp.domain.theme

interface ThemesRepository {
    fun save(theme: Theme)
    fun findAll(): List<Theme>
    fun query(query: ThemesQuery): List<Theme>
    fun nextId(): ThemeId
}