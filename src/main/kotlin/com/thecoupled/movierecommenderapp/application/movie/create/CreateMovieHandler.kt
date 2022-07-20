package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.CommandHandler
import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import org.springframework.stereotype.Service

@Service
class CreateMovieHandler(private val movieCreator: MovieCreator) : CommandHandler<CreateMovieCommand, MovieId> {
    override fun handle(command: CreateMovieCommand): MovieId =
        movieCreator.create(command.toMovieCreatorData()).first.id

    private fun CreateMovieCommand.toMovieCreatorData(): MovieCreatorData =
        MovieCreatorData(
            name = MovieName(this.name),
            genreIds = this.genreIds.map { GenreId.createFromString(it) }.toSet(),
            actorIds = this.actorIds.map { ActorId.createFromString(it) }.toSet(),
            directorIds = this.directorIds.map { DirectorId.createFromString(it) }.toSet(),
            themeIds = this.themeIds.map { ThemeId.createFromString(it) }.toSet(),
            countryId = CountryId.createFromString(this.countryId)
        )
}