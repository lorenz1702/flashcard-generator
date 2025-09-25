package org.example.adapter

import org.example.application.ScriptRunner
import java.io.File

class ScriptRunnerImpl: ScriptRunner {
    override fun run(scriptPath: String, csvPath: String, outputPath: String) {
        try {
            // Der ProcessBuilder nimmt jetzt drei Argumente:
            // 1. Der Python-Interpreter
            // 2. Das auszuführende Skript
            // 3. Das erste Argument für das Skript (dein CSV-Pfad)
            // 4. Das zweite Argument für das Skript (dein finaler .apkg-Pfad)
            val processBuilder = ProcessBuilder("python3", scriptPath, csvPath, outputPath)

            // Setzt das Arbeitsverzeichnis für den Prozess. Wichtig, damit
            // das Skript relative Pfade (wie den zur CSV-Datei) findet.
            processBuilder.directory(File(".")) // Aktuelles Verzeichnis

            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()

            println("--- Python-Skript-Ausgabe ---")
            println(output)
            println("-----------------------------")

        } catch (e: Exception) {
            println("Fehler beim Ausführen des Python-Skripts: ${e.message}")
        }
    }
}
