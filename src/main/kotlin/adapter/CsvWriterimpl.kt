package org.example.adapter
import java.io.File
import Card
import SimpleBackCard
import org.example.application.CsvWriter

class CsvWriterimpl(
    private val mediaDirectory: String = "output/flashcard"
) : CsvWriter {
    override fun write(cards: List<Card>, fileName: String):String {
        val outputFile = File(mediaDirectory, fileName)
        outputFile.parentFile.mkdirs()
        outputFile.bufferedWriter().use { writer ->
            cards.forEach { card ->
                val line = when (card) {
                    is SimpleBackCard -> {
                        val audioFileName = File(card.audiofront).name
                        val back = "${"[sound:$audioFileName]"};${card.hint};${card.back}"
                    }
                }
                writer.write(line)
                writer.newLine()
            }
            return File(mediaDirectory, fileName).absolutePath
        }
    }
}
