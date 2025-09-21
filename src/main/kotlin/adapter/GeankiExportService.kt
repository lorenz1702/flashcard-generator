package org.example.adapter

import org.example.application.AnkiExportService
import org.example.domain.Audio
import org.example.domain.Translation

class GeankiExportService : AnkiExportService {
    override fun exportCard(translation: Translation, audio: Audio): String {
        TODO("Not yet implemented")
        return "/tmp/card.apkg"
    }
}