package com.example.woordup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.woordup.data.model.Word
import com.example.woordup.data.repository.DictionaryRepository
import com.example.woordup.data.repository.UserPreferencesRepository
import com.example.woordup.data.repository.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WordUiState(
    val words: List<Word> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchResult: Word? = null,
    val isSearching: Boolean = false,
    val searchError: String? = null,
    val selectedDiscoveryLevel: String = "B1",
    val selectedDiscoveryCategory: String = "General",
    val discoveryWord: Word? = null,
    val isDiscovering: Boolean = false
)

class WordViewModel(
    private val wordRepository: WordRepository,
    private val dictionaryRepository: DictionaryRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordUiState())
    val uiState: StateFlow<WordUiState> = _uiState.asStateFlow()

    // ... (Keep existing flows: username, level, dailyGoal, dailyProgress) ...

    val username: StateFlow<String> = userPreferencesRepository.username
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    
    val level: StateFlow<Int> = userPreferencesRepository.level
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val dailyGoal: StateFlow<Int> = userPreferencesRepository.dailyGoal
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 10)

    val dailyProgress: StateFlow<Int> = userPreferencesRepository.dailyProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        loadWords()
        // Initialize discovery with default level
        loadNextDiscoveryWord()
    }

    }
    
    fun incrementDailyProgress() {
        viewModelScope.launch {
            val current = dailyProgress.value
            val goal = dailyGoal.value
            if (current < goal) { 
                userPreferencesRepository.updateDailyProgress(current + 1)
            }
        }
    }

    fun loadWords() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            wordRepository.getWords()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { words ->
                    _uiState.update { it.copy(words = words, isLoading = false) }
                }
        }
    }

    fun addWord(englishWord: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val translation = dictionaryRepository.getTranslation(englishWord)
            if (translation != null) {
                val word = Word(english = englishWord, translation = translation)
                try {
                    wordRepository.addWord(word)
                    _uiState.update { it.copy(isLoading = false, searchResult = null) } 
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Failed to save word: ${e.message}") }
                }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Translation not found") }
            }
        }
    }

    fun searchWord(query: String) {
        if (query.isBlank()) {
            _uiState.update { it.copy(searchResult = null, isSearching = false, searchError = null) }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true, searchError = null, searchResult = null) }
            try {
                val translation = dictionaryRepository.getTranslation(query)
                if (translation != null) {
                    _uiState.update { 
                        it.copy(
                            isSearching = false, 
                            searchResult = Word(english = query, translation = translation) 
                        ) 
                    }
                } else {
                    _uiState.update { it.copy(isSearching = false, searchError = "Word not found") }
                }
            } catch (e: Exception) {
                 _uiState.update { it.copy(isSearching = false, searchError = "Network error: ${e.message}") }
            }
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
             try {
                wordRepository.updateWord(word)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Failed to update word: ${e.message}") }
            }
        }
    }

    fun deleteWord(wordId: String) {
        viewModelScope.launch {
            try {
                wordRepository.deleteWord(wordId)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Failed to delete word: ${e.message}") }
            }
        }
    }

    fun saveUser(username: String, level: Int) {
        viewModelScope.launch {
            userPreferencesRepository.saveUser(username, level)
        }
    }

    fun setDiscoveryLevel(newLevel: String) {
        if (_uiState.value.selectedDiscoveryLevel != newLevel) {
            _uiState.update { it.copy(selectedDiscoveryLevel = newLevel) }
            loadNextDiscoveryWord()
        }
    }
    
    fun setDiscoveryCategory(newCategory: String) {
        if (_uiState.value.selectedDiscoveryCategory != newCategory) {
            _uiState.update { it.copy(selectedDiscoveryCategory = newCategory) }
            loadNextDiscoveryWord()
        }
    }

    fun loadNextDiscoveryWord() {
        viewModelScope.launch {
            _uiState.update { it.copy(isDiscovering = true, discoveryWord = null, errorMessage = null) }
            
            // 1. Get words for level & category
            val currentLevel = _uiState.value.selectedDiscoveryLevel
            val currentCategory = _uiState.value.selectedDiscoveryCategory
            
            
            val potentialWords = com.example.woordup.data.WordProvider.getWords(currentLevel, currentCategory)
            
            val existingWords = _uiState.value.words.map { it.english.lowercase().trim() }.toSet()
            var availableWords = potentialWords.filter { it.english.lowercase().trim() !in existingWords }

            if (availableWords.isEmpty() && currentCategory != "General") {
                 val fallbackWords = com.example.woordup.data.WordProvider.getWords(currentLevel, "General")
                 availableWords = fallbackWords.filter { it.english.lowercase().trim() !in existingWords }
            }

            if (availableWords.isEmpty()) {
                _uiState.update { it.copy(isDiscovering = false, errorMessage = "No new words found for $currentLevel. Try a different level!") }
                return@launch
            }

            if (availableWords.isEmpty()) {
                _uiState.update { it.copy(isDiscovering = false, errorMessage = "No new words found for $currentLevel. Try a different level!") }
                return@launch
            }

            val randomDiscoveryWord = availableWords.random()

            _uiState.update { 
                it.copy(
                    isDiscovering = false, 
                    discoveryWord = Word(
                        english = randomDiscoveryWord.english, 
                        translation = randomDiscoveryWord.russian,
                        known = false,
                        level = randomDiscoveryWord.level,
                        category = randomDiscoveryWord.category
                    )
                ) 
            }
        }
    }

    fun onDiscoveryKnow() {
        val wordToSave = _uiState.value.discoveryWord?.copy(known = true)
        if (wordToSave != null) {
            viewModelScope.launch {
                 try {
                     wordRepository.addWord(wordToSave)
                     loadNextDiscoveryWord()
                 } catch (e: Exception) {
                     _uiState.update { it.copy(errorMessage = "Failed to save to Vocabulary") }
                 }
            }
        }
    }

    fun onDiscoveryLearn() {
        val wordToSave = _uiState.value.discoveryWord?.copy(known = false)
        if (wordToSave != null) {
            viewModelScope.launch {
                 try {
                     wordRepository.addWord(wordToSave)
                     loadNextDiscoveryWord()
                 } catch (e: Exception) {
                     _uiState.update { it.copy(errorMessage = "Failed to save to Learning") }
                 }
            }
        }
    }

    fun generateQuizOptions(correctWord: Word): List<String> {
        val allWords = com.example.woordup.data.WordProvider.allDiscoveryWords
        val distractors = allWords
            .filter { it.russian != correctWord.translation }
            .shuffled()
            .take(3)
            .map { it.russian }
        
        return (distractors + correctWord.translation).shuffled()
    }

}

class WordViewModelFactory(
    private val wordRepository: WordRepository,
    private val dictionaryRepository: DictionaryRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(wordRepository, dictionaryRepository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
