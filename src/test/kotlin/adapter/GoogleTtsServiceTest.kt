package adapter


import org.example.adapter.GoogleTtsService
import org.example.domain.Audio
import org.example.domain.Sentence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class GoogleTtsServiceTest {


    @Test
    fun `synthesize sollte bei erfolgreicher API-Antwort eine MP3-Datei erstellen`() {
        // 1. ARRANGE (Vorbereiten)

        // Definiere den Media-Pfad, den wir per Hand übergeben
        val manualMediaPath = "output/media"

        // Sage dem Fake-Server, was er antworten soll


        // Erstelle eine Instanz des Service und gib ihm die URL unseres Fake-Servers
        val service = GoogleTtsService(
            mediaDirectory = manualMediaPath
        )

        val testSentence = Sentence("Hello", "en", "de")

        // 2. ACT (Ausführen)
        val resultAudio: Audio = service.synthesize(testSentence)

        // 3. ASSERT (Überprüfen)

        // Überprüfe das zurückgegebene Audio-Objekt
        assertEquals(testSentence, resultAudio.sentence)

        // Überprüfe die erstellte Datei im Dateisystem
        val expectedFile = File(manualMediaPath, "Hello.mp3")
        assertTrue(expectedFile.exists(), "Die MP3-Datei wurde nicht erstellt.")

    }


}