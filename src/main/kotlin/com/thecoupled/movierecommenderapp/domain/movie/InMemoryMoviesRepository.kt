package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryMoviesRepository(
    existingMovies: Set<Movie> = setOf(),
    private val nextIds: MutableList<MovieId> = mutableListOf()
) : InMemoryRepository<Movie, MovieId>(existingMovies, nextIds), MoviesRepository {

    override fun query(query: MoviesQuery): List<Movie> {
        var returnList: List<Movie> = collection.values.toList()

        if (query.andNames != null) {
            returnList = returnList.filter { movie -> query.andNames!!.contains(movie.name) }
        }

        if (query.andIds != null) {
            returnList = returnList.filter { movie -> query.andIds!!.contains(movie.id) }
        }

        if (query.andExcludeIds != null) {
            returnList = returnList.filterNot { movie -> query.andExcludeIds!!.contains(movie.id) }
        }

        val orFilters = query.orActorIds != null || query.orDirectorIds != null || query.orCountryIds != null || query.orThemeIds != null || query.orGenreIds != null
        if (orFilters) {
            returnList = returnList.filter { movie ->
                query.orGenreIds?.any { genreId -> movie.genreIds.contains(genreId) } == true ||
                    query.orActorIds?.any { actorId -> movie.actorIds.contains(actorId) } == true ||
                    query.orCountryIds?.contains(movie.countryId) == true ||
                    query.orThemeIds?.any { themeId -> movie.themeIds.contains(themeId) } == true ||
                    query.orDirectorIds?.any { directorId -> movie.directorIds.contains(directorId) } == true
            }
        }


        return returnList.toSet().toList()
    }

    override fun nextId(): MovieId = nextIds.removeFirstOrNull() ?: MovieId(UUID.randomUUID())
}

fun createMoviesRepository(
    existingMovies: Set<Movie> = setOf()
): MoviesRepository = InMemoryMoviesRepository(existingMovies)