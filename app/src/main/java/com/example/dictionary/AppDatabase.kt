package com.example.dictionary

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dictionary.model.dao.CategoryDao
import com.example.dictionary.model.dao.WordDao
import com.example.dictionary.model.entities.Category
import com.example.dictionary.model.entities.Word

@Database(entities = [Category::class, Word::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun wordsDao(): WordDao
}