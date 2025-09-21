package org.example.adapter

import org.example.application.TranslationService
import org.example.domain.Sentence
import org.example.domain.Translation


import com.github.kittinunf.fuel.httpGet
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder




class GoogleTranslationService : TranslationService {
    override fun translate(sentence: Sentence): Translation {
        val encodedText = URLEncoder.encode(sentence.text, "UTF-8")

        val url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=${sentence.sourceLang}&tl=${sentence.targetLang}&dt=t&q=$encodedText"

        val (request, response, result) = url.httpGet().responseString()

        var translation = "stubbed translations"

        if (response.statusCode == 200) {
            try {

                val jsonArray = JSONArray(result.get())
                translation = jsonArray.getJSONArray(0).getJSONArray(0).getString(0)

            } catch (e: Exception) {
                translation = "Fehler beim Parsen der Antwort: ${e.message}"
            }
        } else {
            translation = "Fehler bei der Anfrage: Statuscode ${response.statusCode}"
        }

        return Translation(sentence, translation)
    }
}