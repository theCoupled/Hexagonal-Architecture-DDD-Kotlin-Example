package com.thecoupled.movierecommenderapp.api.http.movie.get

import com.thecoupled.movierecommenderapp.api.http.shared.HttpResponsePayload

data class HttpMoviePayload(
    val id: String,
    val name: String
): HttpResponsePayload