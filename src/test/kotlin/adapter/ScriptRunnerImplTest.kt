package org.example.adapter

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.zip.ZipFile

class ScriptRunnerImplIntegrationTest {

    @TempDir
    lateinit var tempDir: File

    // Die zu testende Klasse
    private val scriptRunner = ScriptRunnerImpl()

    @Test // <-- Einfach nur @Test, ohne die @DisabledOnOs Zeile
    fun `run sollte das echte create_deck Skript ausführen und eine valide apkg-Datei erzeugen`() {
        // --- 0. Umgebungs-Check ---
        val scriptFile = File("create_deck.py")
        assumeTrue(scriptFile.exists(), "Das Skript 'create_deck.py' wurde nicht gefunden.")

        // --- 1. ARRANGE (Vorbereiten) ---

        // Erstelle eine temporäre CSV-Datei mit Testdaten
        val testCsvFile = File(tempDir, "test_input.csv")
        testCsvFile.writeText("""
            Hello;Hallo;hello.mp3
            World;Welt;world.mp3
        """.trimIndent())

        // Definiere den Ausgabepfad für die .apkg-Datei
        val expectedApkgFile = File(tempDir, "test_deck.apkg")

        // --- 2. ACT (Ausführen) ---
        scriptRunner.run(
            scriptPath = scriptFile.absolutePath,
            csvPath = testCsvFile.absolutePath,
            outputPath = expectedApkgFile.absolutePath
        )

        // --- 3. ASSERT (Überprüfen) ---

        // Überprüfung 1: Existiert die .apkg-Datei?
        assertTrue(expectedApkgFile.exists(), "Die .apkg-Datei wurde nicht erstellt.")

        // Überprüfung 2: Ist die Datei größer als 0 Bytes?
        assertTrue(expectedApkgFile.length() > 0, "Die .apkg-Datei ist leer.")

        // Überprüfung 3 (Bonus): Ist es ein valides ZIP-Archiv und enthält es die Datenbank?
        try {
            ZipFile(expectedApkgFile).use { zip ->
                val dbEntry = zip.getEntry("collection.anki21")
                assertTrue(dbEntry != null, "Die .apkg-Datei (ZIP) enthält keine 'collection.anki21'-Datenbank.")
            }
        } catch (e: Exception) {
            throw AssertionError("Die erstellte .apkg-Datei ist kein valides ZIP-Archiv.", e)
        }
    }
}