package org.example.application

import org.example.domain.Audio
import org.example.domain.Translation

interface AnkiExportService {
    fun exportCards (ankiFormat: MutableList<String>, outputFilePath: String): String
    //  val fields = "${cardData.originalText}\u001f${cardData.translatedText} [sound:${cardData.audioFileName}]"
}