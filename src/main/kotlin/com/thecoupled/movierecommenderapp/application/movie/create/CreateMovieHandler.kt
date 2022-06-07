package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.application.shared.Handler
import com.thecoupled.movierecommenderapp.domain.actor.ActorName
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.genre.GenreName
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName
import org.springframework.stereotype.Service

@Service
class CreateMovieHandler(private val movieCreator: MovieCreator) : Handler<CreateMovieCommand, MovieId> {
    override fun handle(command: CreateMovieCommand): MovieId =
        movieCreator.create(command.toMovieCreatorData()).first.id

    private fun CreateMovieCommand.toMovieCreatorData(): MovieCreatorData =
        MovieCreatorData(
            name = MovieName(this.name),
            genreNames = this.genreNames.map { GenreName(it) }.toSet(),
            actorNames = this.actorNames.map { ActorName(it) }.toSet(),
            directorNames = this.directorNames.map { DirectorName(it) }.toSet(),
            themeNames = this.themeNames.map { ThemeName(it) }.toSet(),
            countryName = CountryName(this.countryName)
        )
}