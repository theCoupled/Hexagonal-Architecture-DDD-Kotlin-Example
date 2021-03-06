package com.thecoupled.movierecommenderapp.domain.theme
import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryThemesRepository(
    private val existing: Set<Theme> = setOf(),
    private val nextIds: MutableList<ThemeId> = mutableListOf()
) : InMemoryRepository<Theme, ThemeId>(existing, nextIds), ThemesRepository {
    override fun query(query: ThemesQuery): List<Theme> {
        val returnList: MutableList<Theme> = mutableListOf()

        if (query.ids != null) {
            returnList += collection.values.filter { entity -> query.ids.contains(entity.id) }
        }

        return returnList
    }

    override fun nextId(): ThemeId = nextIds.removeFirstOrNull() ?: ThemeId(UUID.randomUUID())
}

fun createThemesRepository(
    existingThemes: Set<Theme> = setOf(),
    nextIds: Set<ThemeId> = setOf()
): ThemesRepository = InMemoryThemesRepository(existingThemes, nextIds.toMutableList())