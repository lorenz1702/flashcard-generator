package application

import org.example.adapter.CsvWriterimpl
import org.example.adapter.GoogleTranslationService
import org.example.adapter.GoogleTtsService
import org.example.adapter.ScriptRunnerImpl
import org.example.adapter.SeperateStringServiceImpl
import org.example.application.AnkiExportService
import org.example.application.CardsToApkgUseCase
import org.example.application.CreateCardsUseCase
// KORRIGIERTE IMPORTE für JUnit 5
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File


class CardsToApkgUseCaseTest {

    private val univeralPath = "flashcard/output/test"

    private val seperatetor = SeperateStringServiceImpl()
    private val translator = GoogleTranslationService()
    private val ttsService = GoogleTtsService(univeralPath)
    private val createCardsUseCaseTest = CreateCardsUseCase(seperatetor, translator, ttsService)

    private val csvWriter = CsvWriterimpl(univeralPath)
    private val scriptRunner = ScriptRunnerImpl()
    private val ankiExportService = AnkiExportService(csvWriter, scriptRunner)

    val cardsToApkgUseCase = CardsToApkgUseCase(ankiExportService, createCardsUseCaseTest)

    @Test
    fun `teste den apkgersteller`() {
        //Arrange
        val inputstring =
            "Ho preso un caffè.\n" +
                    "Ci sono molte persone.\n"
        val targetLang = "DE"
        val sourceLang = "IT"

        // Act
        cardsToApkgUseCase.createApkgfile(inputstring, sourceLang, targetLang, univeralPath)

        // ASSERT

        val expectedApkgFile = File("$univeralPath.apkg") // z.B. "output/test.apkg"
        // Überprüfe, ob die .apkg-Datei am Ende des Prozesses erstellt wurde.
        assertTrue(expectedApkgFile.exists(), "Die finale .apkg-Datei wurde nicht unter ${expectedApkgFile.absolutePath} erstellt.")

        // Bonus-Überprüfung: Ist die Datei nicht leer?
        assertTrue(expectedApkgFile.length() > 0, "Die erstellte .apkg-Datei ist leer.")
    }




}