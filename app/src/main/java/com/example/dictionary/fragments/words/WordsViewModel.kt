package com.example.dictionary.fragments.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.App
import com.example.dictionary.model.entities.Word
import kotlinx.coroutines.launch

class WordsViewModel : ViewModel() {
    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    fun loadWords(categoryId: Int) {
        viewModelScope.launch {
            val response = App.getInstance().db.wordsDao().getWordsByCategory(categoryId)
            _words.value = response
        }
    }
}