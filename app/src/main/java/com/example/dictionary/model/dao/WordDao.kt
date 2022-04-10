package com.example.dictionary.model.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.model.entities.Word

@Dao
interface WordDao {
    @Query("select * from word where categoryId == :categoryId")
    suspend fun getWordsByCategory(categoryId: Int): List<Word>

    @Query("select * from word where isFavorite == 1")
    suspend fun getFavourites(): List<Word>

    @Update
    suspend fun updateWord(word: Word)
}