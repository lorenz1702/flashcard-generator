package org.example.application

import org.example.domain.Sentence

class CreateCardUseCase (
    private val translationService: TranslationService,
    private val ttsService: TtsService,
    private val ankiExportService: AnkiExportService){
    fun execute(sentence: Sentence): String {
        val translation = translationService.translate(sentence)
        val audio = ttsService.synthesize(sentence)
        return ankiExportService.exportCard(translation, audio)
    }
}