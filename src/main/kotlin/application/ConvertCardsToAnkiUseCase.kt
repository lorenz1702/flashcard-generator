package org.example.application

import org.example.domain.FlashcardData

interface AnkiCardFormatter {
    operator fun invoke(cards: List<Card>): AnkiExportResult
}
