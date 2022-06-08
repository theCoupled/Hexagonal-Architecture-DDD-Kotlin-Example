package com.thecoupled.movierecommenderapp.api.http.movie.get

import com.thecoupled.movierecommenderapp.application.movie.find.FindMovieQuery
import com.thecoupled.movierecommenderapp.application.movie.find.FindMovieQueryHandler
import com.thecoupled.movierecommenderapp.application.movie.find.MovieResponse
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(GetMovieController::class)
@AutoConfigureWebClient
class GetMovieControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var findMovieQueryHandler: FindMovieQueryHandler

    @Test
    fun `should return 400 Bad request when handler throws a runtime exception`() {
        val arbitraryMovieId = "some-movie-id"
        given(findMovieQueryHandler.handle(createArbitraryFindMovieQuery()))
            .willThrow(IllegalArgumentException("arbitrary error message"))

        mockMvc.get("/api/v1/movies/$arbitraryMovieId") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
            content {
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                jsonPath("$.errors[0].detail", Matchers.containsString("arbitrary error message"))
                jsonPath("$.errors[0].status", Matchers.containsString("400 BAD_REQUEST"))
            }
        }
    }

    @Test
    fun `should return 500 Internal Server Error when handler throws an exception`() {
        val arbitraryMovieId = "some-movie-id"
        given(findMovieQueryHandler.handle(createArbitraryFindMovieQuery()))
            .willAnswer { throw Exception("arbitrary error message") }

        mockMvc.get("/api/v1/movies/$arbitraryMovieId") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError() }
            content {
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                jsonPath("$.errors[0].detail", Matchers.containsString("arbitrary error message"))
                jsonPath("$.errors[0].status", Matchers.containsString("500 INTERNAL_SERVER_ERROR"))
            }
        }
    }

    @Test
    fun `should return 200 Ok response with payload when handler returns a response`() {
        val arbitraryMovieId = "some-movie-id"
        val arbitraryMovieName = "Some movie name"
        val expectedHandlerResponse = MovieResponse(
            id = arbitraryMovieId,
            name = arbitraryMovieName
        )
        given(findMovieQueryHandler.handle(createArbitraryFindMovieQuery()))
            .willReturn(expectedHandlerResponse)

        mockMvc.get("/api/v1/movies/$arbitraryMovieId") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                jsonPath("$.data.id", Matchers.containsString(arbitraryMovieId))
                jsonPath("$.data.name", Matchers.containsString(arbitraryMovieName))
            }
        }
    }

    private fun createArbitraryFindMovieQuery(): FindMovieQuery = FindMovieQuery(id = "some-movie-id")
}