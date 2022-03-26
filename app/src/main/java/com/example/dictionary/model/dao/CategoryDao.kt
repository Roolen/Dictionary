package com.example.dictionary.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionary.model.entities.Category

@Dao
interface CategoryDao {
    @Query("select * from category")
    suspend fun getAll(): List<Category>
}