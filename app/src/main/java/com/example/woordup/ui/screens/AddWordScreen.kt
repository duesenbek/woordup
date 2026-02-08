package com.example.woordup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.viewmodel.WordViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddWordScreen(
    viewModel: WordViewModel,
    onWordSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var isTranslationVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(uiState.discoveryWord) {
        isTranslationVisible = false
    }
    
    LaunchedEffect(Unit) {
        if (uiState.discoveryWord == null && !uiState.isDiscovering) {
            viewModel.loadNextDiscoveryWord()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Discover Words", color = DeepGreen, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onWordSaved) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = DeepGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Level", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                LevelSelectionRow(
                    selectedLevel = uiState.selectedDiscoveryLevel,
                    onLevelSelected = { viewModel.setDiscoveryLevel(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Topic", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                TopicSelectionRow(
                    selectedTopic = uiState.selectedDiscoveryCategory,
                    onTopicSelected = { viewModel.setDiscoveryCategory(it) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(24.dp))
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isDiscovering) {
                    CircularProgressIndicator(color = DeepGreen)
                } else if (uiState.discoveryWord != null) {
                    val word = uiState.discoveryWord!!
                } else if (uiState.discoveryWord != null) {
                    val word = uiState.discoveryWord!!
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        onClick = { isTranslationVisible = !isTranslationVisible }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = word.english,
                                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                                color = DeepGreen,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            if (isTranslationVisible) {
                                Text(
                                    text = word.translation,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = DeepGreen,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                Text(
                                    text = "Tap to reveal",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else if (uiState.errorMessage != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "No words found", color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.loadNextDiscoveryWord() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { viewModel.onDiscoveryKnow() },
                    enabled = uiState.discoveryWord != null,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepGreen),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(end = 8.dp)
                ) {
                    Icon(Icons.Rounded.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("I Know It")
                }

                    Spacer(modifier = Modifier.width(8.dp))
                    Text("I Know It")
                }
                
                Button(
                    onClick = { viewModel.onDiscoveryLearn() },
                    enabled = uiState.discoveryWord != null,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepGreen),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(start = 8.dp)
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Learn")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun LevelSelectionRow(
    selectedLevel: String,
    onLevelSelected: (String) -> Unit
) {
    val levels = listOf("B1", "B2", "C1", "C2")
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        levels.forEach { level ->
            FilterChip(
                selected = (level == selectedLevel),
                onClick = { onLevelSelected(level) },
                label = { Text(level) },
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
}

@Composable
fun TopicSelectionRow(
    selectedTopic: String,
    onTopicSelected: (String) -> Unit
) {
    val topics = listOf("General", "Business", "Travel", "Tech", "Science", "Politics", "Art", "Sport")
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        topics.forEach { topic ->
            FilterChip(
                selected = (topic == selectedTopic),
                onClick = { onTopicSelected(topic) },
                label = { Text(topic) },
                colors = FilterChipDefaults.filterChipColors(
                label = { Text(topic) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = DeepGreen, 
                    selectedLabelColor = Color.White,
                    labelColor = DeepGreen
                ),
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
}
