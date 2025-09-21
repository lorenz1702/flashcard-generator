package adapter

import org.example.adapter.GoogleTranslationService
import org.example.domain.Sentence
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GoogleTranslationServiceTest {
    @Test
    fun `should return translated sentence`() {
        // given
        val service = GoogleTranslationService() // echte Implementierung
        val input = Sentence("Bonjour", "FR", "EN")

        // when
        val result = service.translate(input)

        // then
        assertNotNull(result)
        assertEquals("FR", result.original.sourceLang)
        assertEquals("EN", result.original.targetLang)
        assertEquals("Bonjour", result.original.text)
        // hier checkst du auf den Übersetzungstext (Stub/Mock oder echter Call)
        // Für echten API-Call wäre z. B.:
        assertEquals("Good morning", result.translated)
    }
}