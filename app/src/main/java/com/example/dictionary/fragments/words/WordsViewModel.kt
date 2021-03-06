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

    private var categoryId = -1
    fun loadWords(categoryId: Int) {
        this.categoryId = categoryId
        viewModelScope.launch {
            val response = App.getInstance().db.wordsDao().getWordsByCategory(categoryId)
            _words.value = response
        }
    }

    fun changeFavorite(word: Word) {
        viewModelScope.launch {
            App.getInstance().db.wordsDao().updateWord(word.copy(isFavorite = word.isFavorite.not()))
            loadWords(categoryId)
        }
    }
}