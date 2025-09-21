package org.example.adapter

import org.example.application.TtsService
import org.example.domain.Audio
import org.example.domain.Sentence

class GoogleTtsService : TtsService {
    override fun synthesize(sentence: Sentence): Audio {
        TODO("Not yet implemented")
        return Audio(sentence, "/tmp/audio.mp3")
    }
}