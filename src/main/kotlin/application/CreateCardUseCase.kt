package org.example.application

import Card
import SimpleBackCard
import org.example.domain.Sentence
import java.util.UUID

class CreateCardsUseCase (
    private val seperateStringService: SeperateStringService,
    //private val ankiExportService: AnkiExportService? = null,
    private val translationService: TranslationService,
    private val ttsService: TtsService

){
    fun createsimplecards(inputstring: String, sourceLang: String, targetLang: String):MutableList<Card>{
        val liststring = seperateStringService.separateString(inputstring)
        val cards = liststring.map { sentenceText ->
            val sentence = Sentence(sentenceText, sourceLang, targetLang)
            val translation = translationService.translate(sentence)
            val audio = ttsService.synthesize(sentence)

            // Create a SimpleBackCard with the results.
            // This card is the result of the 'map' operation for this sentence.
            SimpleBackCard(
                id = "1", // Generate a unique ID
                back = translation.translated,  // The German translation
                audiofront = audio.filePath,        // The audio file name
                hint = sentence.text                // The original Italian sentence
            )
        }

        // 3. Convert the read-only List from 'map' to a MutableList to match the return type
        return cards.toMutableList()
    }



}