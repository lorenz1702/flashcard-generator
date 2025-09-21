package org.example.application

import org.example.domain.Sentence
import org.example.domain.Translation

interface TranslationService {
    fun translate(sentence: Sentence): Translation
}