package com.example.dictionary.api.requests

data class TranslateRequest(
    val texts: List<String>,
    val targetLanguageCode: String = "ru"
)
