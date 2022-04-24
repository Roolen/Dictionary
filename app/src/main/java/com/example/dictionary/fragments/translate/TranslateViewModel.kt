package com.example.dictionary.fragments.translate

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

    fun translate(query: String) {
        viewModelScope.launch {
            try {
                val response =
                    YandexApiService.API.getTranslateAsync(TranslateRequest(listOf(query))).await()
                if (response.translations != null) {
                    _translate.value = response.translations[0].text
                }
            } catch (e: Exception) {

            }
        }
    }
}