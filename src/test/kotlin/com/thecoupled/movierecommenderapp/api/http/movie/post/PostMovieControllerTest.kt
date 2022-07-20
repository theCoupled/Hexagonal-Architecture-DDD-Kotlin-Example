package com.thecoupled.movierecommenderapp.api.http.movie.post

import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieCommand
import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieHandler
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.*


@WebMvcTest(PostMovieController::class)
@AutoConfigureWebClient
class PostMovieControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createMovieHandler: CreateMovieHandler

    @Test
    fun `should return 400 Bad request when handler throws a runtime exception`() {
        given(createMovieHandler.handle(createArbitraryCreateMovieCommand()))
            .willThrow(IllegalArgumentException("arbitrary error message"))

        mockMvc.post("/api/v1/movies") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = createArbitraryRequestBodyPayload()
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
        given(createMovieHandler.handle(createArbitraryCreateMovieCommand()))
            .willAnswer { throw Exception("arbitrary error message") }

        mockMvc.post("/api/v1/movies") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = createArbitraryRequestBodyPayload()
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
    fun `should return 201 Created response when handler returns the created resource id`() {
        val arbitraryResponse = MovieId(UUID.randomUUID())
        given(createMovieHandler.handle(createArbitraryCreateMovieCommand())).willReturn(arbitraryResponse)

        mockMvc.post("/api/v1/movies") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = createArbitraryRequestBodyPayload()
        }.andExpect {
            status { isCreated() }
            header { string(HttpHeaders.LOCATION, "http://localhost/api/v1/movies/${arbitraryResponse.value}") }
        }
    }

    private fun createArbitraryRequestBodyPayload(): String =
        """
            {
                "name": "some movie name",
                "genre_ids": [
                    "some genre"
                ],
                "actor_ids": [
                    "some actor"
                ],
                "director_ids": [
                    "some director"
                ],
                "theme_ids": [
                    "some theme"
                ],
                "country_id": "some country"
            }
        """.trimIndent()

    private fun createArbitraryCreateMovieCommand(): CreateMovieCommand =
        CreateMovieCommand(
            name = "some movie name",
            genreIds = setOf("some genre"),
            actorIds = setOf("some actor"),
            directorIds = setOf("some director"),
            themeIds = setOf("some theme"),
            countryId = "some country"
        )

}