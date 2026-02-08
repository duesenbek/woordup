package com.example.woordup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.woordup.data.model.Word
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.viewmodel.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyListScreen(
    viewModel: WordViewModel,
    onPracticeClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val words = uiState.words.filter { it.known }
    
    var selectedCategory by remember { mutableStateOf("All") }
    
    val filteredWords = remember(words, selectedCategory) {
        if (selectedCategory == "All") {
            words
        } else {
            words.filter { it.category == selectedCategory }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (filteredWords.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = onPracticeClick,
                    containerColor = DeepGreen,
                    contentColor = Color.White,
                    icon = { Icon(Icons.Rounded.PlayArrow, "Practice") },
                    text = { Text("Practice (${filteredWords.size})") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Vocabulary",
                style = MaterialTheme.typography.titleLarge,
                color = DeepGreen,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val categories = listOf("All") + com.example.woordup.data.WordProvider.allCategories
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        selected = (category == selectedCategory),
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DeepGreen,
                            selectedLabelColor = Color.White,
                            labelColor = DeepGreen
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = true,
                            borderColor = DeepGreen,
                            selectedBorderColor = DeepGreen
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (words.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No words in vocabulary yet.", color = Color.Gray)
                }
            } else if (filteredWords.isEmpty()) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No words in this category.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filteredWords, key = { it.id }) { word ->
                        WordCard(
                            word = word,
                            onToggleKnown = { viewModel.updateWord(it) },
                            onDelete = { viewModel.deleteWord(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WordCard(
    word: Word,
    onToggleKnown: (Word) -> Unit,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = word.english,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = word.translation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Surface(
                        color = DeepGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "${word.level} • ${word.category}",
                            style = MaterialTheme.typography.labelSmall,
                            color = DeepGreen,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onToggleKnown(word.copy(known = !word.known)) }) {
                    Icon(
                        imageVector = if (word.known) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                        contentDescription = "Toggle Known",
                        tint = DeepGreen
                    )
                }
                IconButton(onClick = { onDelete(word.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}
