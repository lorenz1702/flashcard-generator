package adapter

import org.example.adapter.SeperateStringServiceImpl
import org.example.application.SeperateStringService


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeperateStringServiceImplTest {

    // Erstellt eine Instanz des zu testenden Services
    private val service: SeperateStringService = SeperateStringServiceImpl()

    @Test
    fun `sollte einen mehrzeiligen String korrekt in eine Liste von Sätzen aufteilen`() {
        // 1. Arrange (Vorbereiten)
        val inputText = """
            Ho preso un caffè.
            Ci sono molte persone.
            Come si dice “Apfel” in italiano?
            Il tempo è buono oggi.
            Incomprensione è normale in una nuova lingua.
            Ci sono i topi nella cantina!
            Voglio andare al mare.
            Questo film è divertente!
            Non ho voglia.
        """.trimIndent() // trimIndent() entfernt überflüssige Einrückungen

        val expectedList = mutableListOf(
            "Ho preso un caffè.",
            "Ci sono molte persone.",
            "Come si dice “Apfel” in italiano?",
            "Il tempo è buono oggi.",
            "Incomprensione è normale in una nuova lingua.",
            "Ci sono i topi nella cantina!",
            "Voglio andare al mare.",
            "Questo film è divertente!",
            "Non ho voglia."
        )

        // 2. Act (Ausführen)
        val result = service.separateString(inputText)

        // 3. Assert (Überprüfen)
        assertEquals(expectedList.size, result.size, "Die Anzahl der Sätze sollte übereinstimmen.")
        assertEquals(expectedList, result, "Die resultierende Liste sollte der erwarteten Liste entsprechen.")
    }

    @Test
    fun `sollte eine leere Liste zurückgeben, wenn der Input leer ist`() {
        // Arrange
        val inputText = ""

        // Act
        val result = service.separateString(inputText)

        // Assert
        assertEquals(0, result.size)
    }

    @Test
    fun `sollte leere Zeilen zwischen den Sätzen ignorieren`() {
        // Arrange
        val inputText = """
            Satz 1.

            Satz 2.
        """.trimIndent()

        // Act
        val result = service.separateString(inputText)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Satz 1.", result[0])
        assertEquals("Satz 2.", result[1])
    }
}