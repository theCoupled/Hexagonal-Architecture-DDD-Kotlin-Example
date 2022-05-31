package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import java.util.*

class InMemoryMoviesRepository(
    existingMovies: Set<Movie> = setOf(),
    private val nextIds: MutableList<MovieId> = mutableListOf()
) : InMemoryRepository<Movie, MovieId>(existingMovies, nextIds), MoviesRepository {

    override fun query(query: MoviesQuery): List<Movie> {
        val returnList: MutableList<Movie> = mutableListOf()

        if (query.names != null) {
            returnList += collection.values.filter { movie -> query.names!!.contains(movie.name) }
        }

        return returnList
    }

    override fun nextId(): MovieId = nextIds.removeFirstOrNull() ?: MovieId(UUID.randomUUID())
}

fun createMoviesRepository(
    existingMovies: Set<Movie> = setOf()
): MoviesRepository = InMemoryMoviesRepository(existingMovies)