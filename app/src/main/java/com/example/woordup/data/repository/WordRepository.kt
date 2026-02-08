package com.example.woordup.data.repository

import com.example.woordup.data.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun addWord(word: Word)
    fun getWords(): Flow<List<Word>>
    suspend fun updateWord(word: Word)
    suspend fun deleteWord(wordId: String)
}
