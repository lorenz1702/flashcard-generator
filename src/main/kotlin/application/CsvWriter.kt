package org.example.application

import Card

interface CsvWriter {
    fun write(cards: List<Card>, fileName: String) :String
}

