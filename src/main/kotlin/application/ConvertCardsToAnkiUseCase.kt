package org.example.application

import Card
import org.example.domain.AnkiExportResult


interface AnkiCardFormatter {
    operator fun invoke(cards: List<Card>): AnkiExportResult
}
