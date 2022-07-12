package com.thecoupled.movierecommenderapp.application.shared.bus.command

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId

interface CommandHandler<C: Command, A: AggregateId> {
    fun handle(command: C): A
}
