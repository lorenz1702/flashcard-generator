package org.example.application

import org.example.domain.Audio
import org.example.domain.Sentence

interface TtsService {
    fun synthesize(sentence: Sentence): Audio
}