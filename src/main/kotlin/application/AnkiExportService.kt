package org.example.application

import Card

// Der Service ist jetzt von den Implementierungen entkoppelt.
class AnkiExportService(
    private val csvWriter: CsvWriter,
    private val scriptRunner: ScriptRunner
) {
    // Diese Methode bleibt dein öffentlicher Einstiegspunkt.
    fun exportCards(cards: List<Card>, csvPath: String) {


        // Delegiere das Schreiben der CSV-Datei an den CsvWriter.
        csvWriter.write(cards, csvPath)

        // Delegiere das Ausführen des Skripts an den ScriptRunner.
        scriptRunner.run("src/main/kotlin/adapter/create_deck.py", csvPath, csvPath)
    }
}
