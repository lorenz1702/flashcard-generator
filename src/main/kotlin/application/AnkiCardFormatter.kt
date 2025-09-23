package org.example.application

import org.example.domain.FlashcardData

interface AnkiCardFormatter {
    fun format(cardData: FlashcardData): String
}