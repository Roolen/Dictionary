package com.example.dictionary.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionary.model.entities.Word

@Dao
interface WordDao {
    @Query("select * from word where categoryId == :categoryId")
    suspend fun getWordsByCategory(categoryId: Int): List<Word>
}