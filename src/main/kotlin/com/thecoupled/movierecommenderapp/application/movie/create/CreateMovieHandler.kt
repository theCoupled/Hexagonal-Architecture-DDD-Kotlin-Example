package com.thecoupled.movierecommenderapp.application.movie.create

import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.Validated
import arrow.core.ValidatedNel
import arrow.core.invalidNel
import arrow.core.traverse
import arrow.core.validNel
import arrow.core.zip
import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.shared.error.DomainErrors
import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidInput
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateMovieHandler(private val movieCreator: MovieCreator) {
    fun handle(command: CreateMovieCommand): Validated<DomainErrors, String> {
        val movieCreatorDataSampleNel =
            MovieName(command.name)
                .zip(
                    CountryId.createFromString(command.countryId).toValidatedNel(),
                    command.genreIds.traverse { GenreId.createFromString(it) },
                    command.themeIds.traverse { ThemeId.createFromString(it) },
                    command.directorIds.traverse { DirectorId.createFromString(it) },
                    command.actorIds.traverse { ActorId.createFromString(it) }
                ) { name, countryId, genreIds, themeIds, directorIds, actorIds ->
                    movieCreator.create(
                    MovieCreatorDataSample(
                        name = name,
                        countryId = countryId,
                        genreIds = genreIds.toSet(),
                        themeIds = themeIds.toSet(),
                        directorIds = directorIds.toSet(),
                        actorIds = actorIds.toSet()
                    )
                }

        return when (movieCreatorDataSampleNel) {
            is Valid -> movieCreator.create(movieCreatorDataSampleNel.value)
            is Invalid -> movieCreatorDataSampleNel
        }
    }


    /*
    MovieCreatorData.create(
        name = MovieName(this.name),
        genreIds = this.genreIds.map { GenreId.create(UUID.fromString(it)).zip(
            it.toUuId()
        ) }.toSet(),
        actorIds = this.actorIds.map { ActorId(UUID.fromString(it)) }.toSet(),
        directorIds = this.directorIds.map { DirectorId(UUID.fromString(it)) }.toSet(),
        themeIds = this.themeIds.map { ThemeId(UUID.fromString(it)) }.toSet(),
        countryId = CountryId(UUID.fromString(this.countryId))
    )

     */


    private fun String.toUuId(): ValidatedNel<InvalidInput, UUID> =
        try {
            UUID.fromString(this).validNel()
        } catch (e: Exception) {
            InvalidInput().invalidNel()
        }
}