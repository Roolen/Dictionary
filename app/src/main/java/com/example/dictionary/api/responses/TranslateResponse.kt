package com.example.dictionary.api.responses

data class TranslateResponse(
    val translations: List<Translate>?
)

data class Translate(
    val text: String,
    val detectedLanguageCode: String
)
