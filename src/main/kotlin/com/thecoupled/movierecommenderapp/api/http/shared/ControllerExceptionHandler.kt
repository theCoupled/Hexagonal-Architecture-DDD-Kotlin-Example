package com.thecoupled.movierecommenderapp.api.http.shared

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<JsonApiErrorResponse> {
        return createJsonApiErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            details = ex.localizedMessage ?: ""
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: Exception, request: WebRequest): ResponseEntity<JsonApiErrorResponse> {
        return createJsonApiErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            details = ex.localizedMessage ?: ""
        )
    }

    fun createJsonApiErrorResponse(
        status: HttpStatus,
        details: String
    ): ResponseEntity<JsonApiErrorResponse> {
        val responseHeaders = HttpHeaders()
        responseHeaders[HttpHeaders.CONTENT_TYPE] = MediaType.APPLICATION_JSON.toString()
        return ResponseEntity
            .status(status)
            .headers(responseHeaders)
            .body(
                JsonApiErrorResponse(
                    errors = listOf(
                        JsonApiError(
                            detail = details,
                            status = status.toString()
                        )
                    )
                )
            )
    }

    data class JsonApiErrorResponse(
        val errors: List<JsonApiError>
    )

    data class JsonApiError(
        val detail: String,
        val status: String
    )

}