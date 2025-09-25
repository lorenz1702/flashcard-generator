class AnkiConversionAdapter : ConvertCardsToAnkiUseCase {

    override operator fun invoke(cards: List<Card>): AnkiExportResult {
        val notesContent = mutableListOf<String>()

        cards.forEach { card ->
            val noteLine = convertSingleCardToAnkiNote(card)
            notesContent.add(noteLine)

            // 1. Alle erforderlichen Mediendateien sammeln
            card.audioFileName?.let {
                requiredMediaFiles.add(it)
            }
        }

        // 2. Das Ergebnis mit den Notizen und den Dateinamen zurückgeben
        return AnkiExportResult(notesContent, requiredMediaFiles)
    }

    // Die EINE Methode, die alle Typen unterscheidet
    private fun convertSingleCardToAnkiNote(card: Card): String {
        
        // Fügt den Anki-Media-Tag hinzu, falls eine Audiodatei vorhanden ist
        val audioTag = card.audioFileName?.let { "[sound:$it]" } ?: ""

        return when (card) {
            is BasicCard -> {
                // Audio-Tag am Ende der Vorder- oder Rückseite hinzufügen
                "${card.id}\t${card.front}\t${card.back} $audioTag" 
            }
        }
    }
}
