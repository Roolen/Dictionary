package com.example.dictionary.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.App
import com.example.dictionary.model.entities.Category
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun loadCategories() {
        viewModelScope.launch {
            val response = App.getInstance().db.categoryDao().getAll()
            _categories.value = response
        }
    }
}