package org.example.application

import org.example.domain.Audio
import org.example.domain.Translation

interface AnkiExportService {
    fun exportCard (translation: Translation, audio: Audio): String
}