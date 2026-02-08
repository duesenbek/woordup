package com.example.woordup.network

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryApi {
    @GET("entries/en/{word}")
    suspend fun getWordDefinition(@Path("word") word: String): List<DictionaryEntry>
}

data class DictionaryEntry(
    val word: String,
    val meanings: List<Meaning>
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

data class Definition(
    val definition: String
)
