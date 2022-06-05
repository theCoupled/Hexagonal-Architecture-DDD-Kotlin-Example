package com.thecoupled.movierecommenderapp.application.recommendation.create

import com.thecoupled.movierecommenderapp.domain.like.LikesRepository
import com.thecoupled.movierecommenderapp.domain.like.createLikesRepository
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.createMoviesRepository
import com.thecoupled.movierecommenderapp.domain.recommendation.Recommendation
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.shared.UserCaseSetup
import com.thecoupled.movierecommenderapp.domain.shared.userCaseSetup
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import com.thecoupled.movierecommenderapp.domain.user.createUsersRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError

class CreateRecommendationsHandlerTest {

    @Test
    fun `should not recommend any movie when no movies available`() {
        val userCaseSetup = userCaseSetup {
            likedMovies {
                movie {
                    name = "arbitrary movie name"
                    country = "arbitrary country name"
                    genres { genre("arbitrary genre") }
                    directors { director("arbitrary director") }
                    actors { }
                    themes { theme("arbitrary theme") }
                }
            }
            notViewedMovies { }
            expectedMovieRecommendations { }
        }

        val (result, _) = executeWithUserCaseSetup(userCaseSetup)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should not recommend any movie when user did not like any movie`() {
        val userCaseSetup = userCaseSetup {
            likedMovies { }
            notViewedMovies {
                movie {
                    name = "arbitrary movie name"
                    country = "arbitrary country name"
                    genres { genre("arbitrary genre") }
                    directors { director("arbitrary director") }
                    actors { }
                    themes { theme("arbitrary theme") }
                }
                movie {
                    name = "arbitrary movie name 2"
                    country = "arbitrary country name 2"
                    genres { genre("arbitrary genre 2") }
                    directors { director("arbitrary director 2") }
                    actors { }
                    themes { theme("arbitrary theme 2") }
                }
            }
            expectedMovieRecommendations { }
        }

        val (result, _) = executeWithUserCaseSetup(userCaseSetup)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should recommend movies without affinity when movies with affinity not found`() {
        val userCaseSetup = userCaseSetup {
            likedMovies {
                movie {
                    name = "arbitrary movie name"
                    country = "arbitrary country name"
                    genres { genre("arbitrary genre") }
                    directors { director("arbitrary director") }
                    actors { }
                    themes { theme("arbitrary theme") }
                }
            }
            notViewedMovies {
                movie {
                    name = "arbitrary movie name 2"
                    country = "arbitrary country name 2"
                    genres { genre("arbitrary genre 2") }
                    directors { director("arbitrary director 2") }
                    actors { }
                    themes { theme("arbitrary theme 2") }
                }
            }
            expectedMovieRecommendations {
                recommendation("arbitrary movie name 2", 0.0)
            }
        }

        val (result, _) = executeWithUserCaseSetup(userCaseSetup)

        assertRecommendations(userCaseSetup, result)
    }

    @Test
    fun `should first recommend movies with affinity and after movies without affinity after when recommended movies is less than 5`() {
        val userCaseSetup = userCaseSetup {
            likedMovies {
                movie {
                    name = "movie 1"
                    country = "country 1"
                    genres { genre("genre 1") }
                    directors { director("director 1") }
                    actors { }
                    themes { theme("theme 1") }
                }
            }
            notViewedMovies {
                movie {
                    name = "movie 2"
                    country = "country 1"
                    genres { genre("genre 2") }
                    directors { director("director 2") }
                    actors { }
                    themes { theme("theme 2") }
                }
                movie {
                    name = "movie 3"
                    country = "country 3"
                    genres { genre("genre 3") }
                    directors { director("director 3") }
                    actors { }
                    themes { theme("theme 3") }
                }
            }
            expectedMovieRecommendations {
                recommendation("movie 2", 0.0)
                recommendation("movie 3", 0.0)
            }
        }

        val (result, _) = executeWithUserCaseSetup(userCaseSetup)

        assertRecommendations(userCaseSetup, result)
    }

    private fun assertRecommendations(userCaseSetup: UserCaseSetup, returnedRecommendations: List<Recommendation>) {
        if (userCaseSetup.expectedRecommendations.size != returnedRecommendations.size) {
            throw AssertionFailedError(
                "Expected different recommendations amount ${userCaseSetup.expectedRecommendations.size} != ${returnedRecommendations.size}"
            )
        }

        userCaseSetup.expectedRecommendations.zip(returnedRecommendations).forEach { recommendationsPair ->
            val isDataNotEqual = recommendationsPair.first.movieId != recommendationsPair.second.movieId ||
                recommendationsPair.first.score != recommendationsPair.second.score

            if (isDataNotEqual) {
                throw AssertionFailedError(
                    "Recommendations are not equals ${recommendationsPair.first} != ${recommendationsPair.second}",
                    recommendationsPair.first,
                    recommendationsPair.second
                )
            }
        }
    }

    private fun executeWithUserCaseSetup(userCaseSetup: UserCaseSetup): Pair<List<Recommendation>, List<DomainEvent>> =
        createUseCase(
            usersRepository = createUsersRepository(setOf(userCaseSetup.user)),
            moviesRepository = createMoviesRepository(userCaseSetup.movies),
            likesRepository = createLikesRepository(userCaseSetup.likes)
        ).execute(CreateRecommendationsCommand(userCaseSetup.user.id))

    private fun createUseCase(
        usersRepository: UsersRepository = createUsersRepository(),
        moviesRepository: MoviesRepository = createMoviesRepository(),
        likesRepository: LikesRepository = createLikesRepository()
    ): CreateRecommendationsHandler =
        CreateRecommendationsHandler(
            usersRepository = usersRepository,
            moviesRepository = moviesRepository,
            likesRepository = likesRepository
        )
}