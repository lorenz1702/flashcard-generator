package application

import org.example.adapter.CsvWriterimpl
import org.example.adapter.GoogleTranslationService
import org.example.adapter.GoogleTtsService
import org.example.adapter.ScriptRunnerImpl
import org.example.adapter.SeperateStringServiceImpl
import org.example.application.AnkiExportService
import org.example.application.CreateCardsUseCase
import org.example.application.SeperateStringService


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class CreateCardsUseCaseTest {
    private val seperatetor = SeperateStringServiceImpl()
    private val translator = GoogleTranslationService()
    private val ttsService = GoogleTtsService()
    private val createCardsUseCaseTest = CreateCardsUseCase(seperatetor, translator, ttsService

    )

    @Test
    // Arrange
    fun `erstellt cards aus der Liste`() {
        val inputstring =
            "Ho preso un caffè.\n" +
                    "Ci sono molte persone.\n"
        val targetLang = "DE"
        val sourceLang = "IT"


        //Act
        val cardlist = createCardsUseCaseTest.createsimplecards(inputstring, sourceLang, targetLang)

        // ASSERT
        // Überprüfe, ob die richtige Anzahl an Karten erstellt wurde
        assertEquals(2, cardlist.size, "Es sollten genau 3 Karten erstellt werden.")

    }


}