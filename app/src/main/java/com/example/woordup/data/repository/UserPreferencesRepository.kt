package com.example.woordup.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val LEVEL_KEY = intPreferencesKey("level")
        val DAILY_GOAL_KEY = intPreferencesKey("daily_goal")
        val DAILY_PROGRESS_KEY = intPreferencesKey("daily_progress")
    }

    val username: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }

    val level: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[LEVEL_KEY] ?: 0
        }

    val dailyGoal: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[DAILY_GOAL_KEY] ?: 10
        }

    val dailyProgress: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[DAILY_PROGRESS_KEY] ?: 0
        }

    suspend fun saveUser(username: String, level: Int) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[LEVEL_KEY] = level
        }
    }

    suspend fun updateDailyProgress(progress: Int) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_PROGRESS_KEY] = progress
        }
    }
}
