package org.example.adapter
import java.io.File
import Card
import SimpleBackCard
import org.example.application.CsvWriter

class CsvWriterimpl: CsvWriter {
    override fun write(cards: List<Card>, outputPath: String): String {
        File(outputPath).bufferedWriter().use { writer ->

            cards.forEach { card ->
                val line = when (card) {
                    is SimpleBackCard -> {
                        val back = "${card.hint} [sound:${card.audiofront}]"
                        "${card.back}"
                    }
                }
                writer.write(line)
                writer.newLine()
            }
            return outputPath
        }
    }
}