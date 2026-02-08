package com.example.woordup.data.repository

import com.example.woordup.network.DictionaryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DictionaryRepository(private val api: DictionaryApi) {
    suspend fun getTranslation(word: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWordDefinition(word)
                response.firstOrNull()?.meanings?.firstOrNull()?.definitions?.firstOrNull()?.definition
            } catch (e: Exception) {
                null
            }
        }
    }
}
