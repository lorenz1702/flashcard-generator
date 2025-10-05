package org.example.adapter
import java.io.File
import Card
import SimpleBackCard
import org.example.application.CsvWriter

class CsvWriterimpl(
    private val mediaDirectory: String
) : CsvWriter {
    override fun write(cards: List<Card>, fileName: String):String {
        val outputFile = File(mediaDirectory, fileName)
        outputFile.parentFile.mkdirs()
        outputFile.bufferedWriter().use { writer ->
            cards.forEach { card ->
                val line = when (card) {
                    is SimpleBackCard -> {
                        val back = "${card.hint}; [sound:${card.audiofront}]; ${card.back}"
                        back
                    }
                }
                writer.write(line)
                writer.newLine()
            }
            return File(mediaDirectory, fileName).absolutePath
        }
    }
}