package com.example.dictionary.fragments.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.App
import com.example.dictionary.model.entities.Word
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel() {
    private val _favourites = MutableLiveData<List<Word>>()
    val favourites: LiveData<List<Word>> = _favourites

    fun loadFavorites() {
        viewModelScope.launch {
            val response = App.getInstance().db.wordsDao().getFavourites()
            _favourites.value = response
        }
    }

    fun changeFavorite(word: Word) {
        viewModelScope.launch {
            App.getInstance().db.wordsDao().updateWord(word.copy(isFavorite = word.isFavorite.not()))
            loadFavorites()
        }
    }
}