package org.example.adapter

import org.example.application.TtsService
import org.example.domain.Audio
import org.example.domain.Sentence

import com.github.kittinunf.fuel.httpGet
import java.io.File
import java.net.URLEncoder
import java.util.UUID

class GoogleTtsService (
    private val mediaDirectory: String
) : TtsService {

    init {
        File(mediaDirectory).mkdirs()
    }
    override fun synthesize(sentence: Sentence): Audio {
        // Der Text, der gesprochen werden soll, ist der Originaltext des Satzes
        val textToSpeak = sentence.text
        val languageCode = sentence.sourceLang.lowercase() // z.B. "it" für Italienisch

        val encodedText = URLEncoder.encode(textToSpeak, "UTF-8")
        val url = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&q=$encodedText&tl=$languageCode"

        val (request, response, result) = url.httpGet().response()

        if (response.statusCode == 200) {
            val audioBytes = result.get()

            // Erzeuge einen Dateinamen, der sich auf den Satz bezieht (optional, aber nützlich)
            val fileName = "${textToSpeak.take(15).replace(" ", "_")}.mp3"
            val outputFile = File(mediaDirectory, fileName)

            outputFile.writeBytes(audioBytes)

            println("Audio für '${sentence.text}' erfolgreich erstellt: ${outputFile.path}")

            // *** HIER IST DIE ÄNDERUNG ***
            // Übergib das komplette 'sentence'-Objekt an den Audio-Konstruktor.
            return Audio(sentence = sentence, filePath = outputFile.absolutePath)
        } else {
            println("Fehler beim Abrufen der Audiodatei für '${sentence.text}': Statuscode ${response.statusCode}")
            throw RuntimeException("TTS-Anfrage fehlgeschlagen mit Statuscode ${response.statusCode}")
        }
    }
}