package org.example.application



class CardsToApkgUseCase (
    private val ankiExportService: AnkiExportService,
    private val createCardsUseCase: CreateCardsUseCase
){
    fun createApkgfile(inputstring: String, sourceLang: String, targetLang: String, csvPath: String){
        val cards = createCardsUseCase.createsimplecards(inputstring, sourceLang, targetLang)
        ankiExportService.exportCards(cards, csvPath)

    }
}