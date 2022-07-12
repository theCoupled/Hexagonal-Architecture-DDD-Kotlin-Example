package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidInput

sealed class ActorValidationError : InvalidInput() {
    object ActorNameEmpty : ActorValidationError()
}