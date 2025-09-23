package org.example.adapter

import org.example.application.SeperateStringService

class SeperateStringServiceImpl : SeperateStringService {

    override fun separateString(inputString: String): MutableList<String> {
        return inputString
            .lines() // Teilt den String in eine Liste bei jedem Zeilenumbruch
            .filter { it.isNotBlank() } // Entfernt leere oder nur aus Leerzeichen bestehende Zeilen
            .toMutableList() // Wandelt die schreibgeschützte Liste in eine veränderbare um
    }

}