package com.idigitalstudios.cineexplorerapp.data.mapper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.idigitalstudios.cineexplorerapp.data.local.entity.ShowEntity
import com.idigitalstudios.cineexplorerapp.data.remote.dto.CastMemberDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.CharacterDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.EpisodeDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.ImageDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.PersonDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.RatingDto
import com.idigitalstudios.cineexplorerapp.data.remote.dto.ShowDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowMapperTest {

    private val image = ImageDto(medium = "https://img.com/medium.jpg", original = "https://img.com/original.jpg")

    private val showDto = ShowDto(
        id = 1,
        name = "Breaking Bad",
        language = "English",
        genres = listOf("Drama", "Crime"),
        status = "Ended",
        rating = RatingDto(average = 9.5),
        image = image,
        summary = "<p>A chemistry teacher.</p>"
    )

    // --- ShowDto.toEntity ---

    @Test
    fun showDto_toEntity_mapsAllFields() {
        val entity = showDto.toEntity()
        assertEquals(1, entity.id)
        assertEquals("Breaking Bad", entity.name)
        assertEquals("A chemistry teacher.", entity.summary)
        assertEquals("https://img.com/medium.jpg", entity.imageUrl)
        assertEquals(9.5, entity.rating)
        assertEquals("Drama,Crime", entity.genres)
        assertEquals("Ended", entity.status)
        assertEquals("English", entity.language)
        assertFalse(entity.isFavorite)
    }

    @Test
    fun showDto_toEntity_setsIsFavoriteTrue() {
        assertTrue(showDto.toEntity(isFavorite = true).isFavorite)
    }

    @Test
    fun showDto_toEntity_handlesNullRating() {
        assertNull(showDto.copy(rating = null).toEntity().rating)
    }

    @Test
    fun showDto_toEntity_handlesNullImage() {
        assertNull(showDto.copy(image = null).toEntity().imageUrl)
    }

    @Test
    fun showDto_toEntity_stripsHtmlFromSummary() {
        val entity = showDto.copy(summary = "<b>Bold</b> and <i>italic</i>.").toEntity()
        assertEquals("Bold and italic.", entity.summary)
    }

    @Test
    fun showDto_toEntity_handleNullSummary() {
        assertEquals("", showDto.copy(summary = null).toEntity().summary)
    }

    // --- ShowEntity.toDomain ---

    @Test
    fun showEntity_toDomain_splitGenresCorrectly() {
        val entity = ShowEntity(1, "Show", "", null, null, "Drama,Crime", "Running", null)
        assertEquals(listOf("Drama", "Crime"), entity.toDomain().genres)
    }

    @Test
    fun showEntity_toDomain_returnsEmptyGenresWhenBlank() {
        val entity = ShowEntity(1, "Show", "", null, null, "", "Running", null)
        assertTrue(entity.toDomain().genres.isEmpty())
    }

    @Test
    fun showEntity_toDomain_preservesIsFavorite() {
        val entity = ShowEntity(1, "Show", "", null, null, "", "Running", null, isFavorite = true)
        assertTrue(entity.toDomain().isFavorite)
    }

    // --- CastMemberDto.toDomain ---

    @Test
    fun castMemberDto_toDomain_mapsAllFields() {
        val dto = CastMemberDto(
            person = PersonDto(id = 10, name = "Bryan Cranston", image = image),
            character = CharacterDto(name = "Walter White")
        )
        val member = dto.toDomain()
        assertEquals(10, member.id)
        assertEquals("Bryan Cranston", member.personName)
        assertEquals("Walter White", member.characterName)
        assertEquals("https://img.com/medium.jpg", member.imageUrl)
    }

    @Test
    fun castMemberDto_toDomain_handlesNullPersonImage() {
        val dto = CastMemberDto(
            person = PersonDto(id = 10, name = "Actor", image = null),
            character = CharacterDto(name = "Character")
        )
        assertNull(dto.toDomain().imageUrl)
    }

    // --- EpisodeDto.toDomain ---

    @Test
    fun episodeDto_toDomain_mapsAllFields() {
        val dto = EpisodeDto(
            id = 100, name = "Pilot", season = 1, number = 1,
            image = image, summary = "<p>Pilot episode.</p>"
        )
        val episode = dto.toDomain()
        assertEquals(100, episode.id)
        assertEquals("Pilot", episode.name)
        assertEquals(1, episode.season)
        assertEquals(1, episode.number)
        assertEquals("https://img.com/medium.jpg", episode.imageUrl)
        assertEquals("Pilot episode.", episode.summary)
    }

    @Test
    fun episodeDto_toDomain_handlesNullSummary() {
        val dto = EpisodeDto(id = 1, name = "Ep", season = 1, number = 1, image = null, summary = null)
        assertNull(dto.toDomain().summary)
    }
}
