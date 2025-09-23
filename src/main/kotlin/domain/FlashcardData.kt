package org.example.domain

data class FlashcardData(
    val originalSentence: Sentence,
    val TranslatedSentnce: Translation,
    val audiofile: Audio
)
