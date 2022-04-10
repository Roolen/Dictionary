package com.example.dictionary.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "translate") val translate: String,
    @ColumnInfo(name = "transcription") val transcription: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
    @ColumnInfo(name = "categoryId") val categoryId: Int,
    @ColumnInfo(name = "sound") val sound: ByteArray?
)
