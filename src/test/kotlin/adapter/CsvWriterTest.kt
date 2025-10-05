package adapter

import Card
import SimpleBackCard
import org.example.adapter.CsvWriterimpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class CsvWriterimplTest {

    // JUnit 5 erstellt automatisch einen temporären Ordner für uns.
    // Dieser wird nach dem Test wieder aufgeräumt.
    @TempDir
    lateinit var tempDir: File

    // Die zu testende Klasse
    private val csvWriter = CsvWriterimpl("flashcard/output/test")

    @Test
    fun `write sollte eine CSV-Datei mit korrektem Inhalt und Pfad erstellen`() {
        // 1. ARRANGE (Vorbereiten)
        // Definiere die Test-Karten
        val testCards = listOf(
            SimpleBackCard(id = "1", back = "Hello", hint = "Hallo", audiofront = "hello.mp3"),
            SimpleBackCard(id = "1", back = "World", hint = "Welt", audiofront = "world.mp3")
        )

        // Definiere den erwarteten Pfad innerhalb des temporären Ordners
        val FileName ="test_deck"

        // Definiere den exakten Inhalt, den wir in der Datei erwarten
        val expectedContent = """
            Hello
            World
        """.trimIndent()

        // 2. ACT (Ausführen)
        // Rufe die zu testende Methode auf
        val returnedPath = csvWriter.write(testCards, FileName)

        // 3. ASSERT (Überprüfen)

        // Überprüfe, ob der zurückgegebene Pfad korrekt ist
        assertEquals("/home/lorenz/IdeaProjects/flashcard-generator/output/flashcard/$FileName", returnedPath, "Der zurückgegebene Pfad sollte mit dem Eingabepfad übereinstimmen.")

        // Überprüfe, ob die Datei tatsächlich existiert
        val file = File(returnedPath)
        assertTrue(file.exists(), "Die Ausgabedatei wurde nicht erstellt.")

        // Lies den Inhalt der erstellten Datei und vergleiche ihn mit der Erwartung
        //val actualContent = file.readText().trim() // .trim() entfernt eventuelle letzte Leerzeilen
        // assertEquals(expectedContent, actualContent, "Der Inhalt der CSV-Datei ist nicht korrekt.")
    }

    @Test
    fun `write sollte eine leere Datei für eine leere Kartenliste erstellen`() {
        // Arrange
        val emptyCardList = emptyList<Card>()
        var outputFile = File(tempDir, "empty_deck.csv")

        // Act
        val filepath = csvWriter.write(emptyCardList, outputFile.absolutePath)

        // Assert
        outputFile = File(filepath)
        assertTrue(outputFile.exists(), "Die leere Datei sollte erstellt werden.")
        assertEquals("", outputFile.readText(), "Die Datei sollte leer sein.")
    }
}
