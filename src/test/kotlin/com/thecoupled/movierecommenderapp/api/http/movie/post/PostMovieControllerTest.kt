package com.thecoupled.movierecommenderapp.api.http.movie.post

import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieCommand
import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieHandler
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@WebMvcTest(PostMovieController::class)
@AutoConfigureWebClient
class PostMovieControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createMovieHandler: CreateMovieHandler

    @Test
    fun `should return 400 Bad request when handler throws a runtime exception`() {
        val arbitraryBodyRequestPayload = """
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
        val expectedCommand = CreateMovieCommand(
            name = "some movie name",
            genreNames = setOf("some genre"),
            actorNames = setOf("some actor"),
            directorNames = setOf("some director"),
            themeNames = setOf("some theme"),
            countryName = "some country"
        )

        given(createMovieHandler.handle(expectedCommand)).willThrow(IllegalArgumentException("some message"))

        mockMvc.post("/api/v1/movies") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = arbitraryBodyRequestPayload
        }.andExpect {
            status { isBadRequest() }
        }
    }
}