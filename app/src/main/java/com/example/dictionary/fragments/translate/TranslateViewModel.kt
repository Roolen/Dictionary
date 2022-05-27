package com.example.dictionary.fragments.translate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.api.YandexApiService
import com.example.dictionary.api.requests.TranslateRequest
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel() {
    private val _translate = MutableLiveData("")
    val translate: LiveData<String> = _translate

    fun translate(query: String, langCode: String = "ru") {
        viewModelScope.launch {
            try {
                val response =
                    YandexApiService.API.getTranslateAsync(
                        TranslateRequest(
                            listOf(query),
                            langCode
                        )
                    ).await()
                if (response.translations != null) {
                    _translate.value = response.translations[0].text
                }
            } catch (e: Exception) {

            }
        }
    }

    val codeLanguage = MutableLiveData<Pair<String, String>>()

    fun detectLanguage(text: String) {
        viewModelScope.launch {
            try {
                val response = YandexApiService.API.detectLanguage(text)
                codeLanguage.value =
                    when (response.languageCode) {
                        "ru" -> Pair(text, "en")
                        "en" -> Pair(text, "ru")
                        else -> Pair(text, "ru")
                    }
            } catch (e: Exception) {
                Log.e("Error", e.message ?: "")
            }
        }
    }

    var lang = "ru-RU"

    private val _speech = MutableLiveData<ByteArray>()
    val speech: LiveData<ByteArray> = _speech

    fun speech(text: String) {
        viewModelScope.launch {
            try {
                val response = YandexApiService.API.getSpeech(
                    text = text,
                    language = lang
                )
                _speech.value = response.body()?.byteStream()?.readBytes()
            } catch (e: Exception) {
                Log.e("Error", e.message ?: "")
            }
        }
    }
}