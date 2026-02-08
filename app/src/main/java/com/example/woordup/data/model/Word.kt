package com.example.woordup.data.model

data class Word(
    val id: String = "",
    val english: String = "",
    val translation: String = "",
    val known: Boolean = false,
    val level: String = "B1",
    val category: String = "General"
)
