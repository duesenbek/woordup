package com.example.woordup.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.woordup.data.model.Word
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.ui.theme.ErrorRed
import com.example.woordup.ui.theme.White
import com.example.woordup.viewmodel.WordViewModel

@Composable
fun QuizOptionButton(
    text: String,
    isSelected: Boolean,
    isCorrectAnswer: Boolean,
    isAnswerRevealed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isAnswerRevealed && isCorrectAnswer -> DeepGreen // Correct answer always green when revealed
        isAnswerRevealed && isSelected && !isCorrectAnswer -> ErrorRed // Selected wrong answer red
        isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) // Just selected state (before check)
        else -> MaterialTheme.colorScheme.surface
    }
    
    val contentColor = when {
        isAnswerRevealed && isCorrectAnswer -> Color.White
        isAnswerRevealed && isSelected && !isCorrectAnswer -> Color.White
        else -> MaterialTheme.colorScheme.onSurface
    }

    val borderColor = when {
        isAnswerRevealed && isCorrectAnswer -> DeepGreen
        isAnswerRevealed && isSelected && !isCorrectAnswer -> ErrorRed
        isSelected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = !isAnswerRevealed || (isAnswerRevealed && isCorrectAnswer) 
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    viewModel: WordViewModel,
    onBackClick: () -> Unit,
    reviewMode: ReviewMode = ReviewMode.LEARNING // Default to Learning
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val wordsToReview = remember(uiState.words, reviewMode) { 
        when (reviewMode) {
            ReviewMode.LEARNING -> uiState.words.filter { !it.known }.shuffled()
            ReviewMode.VOCABULARY -> uiState.words.filter { it.known }.shuffled()
        }
    }
    
    var currentIndex by remember { mutableStateOf(0) }
    val currentWord = wordsToReview.getOrNull(currentIndex)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Review", color = DeepGreen, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentWord != null) {
                // Quiz State
                var selectedOption by remember { mutableStateOf<String?>(null) }
                var isCorrect by remember { mutableStateOf(false) }
                
                val options = remember(currentWord) { viewModel.generateQuizOptions(currentWord) }

                // Reset state on word change
                LaunchedEffect(currentWord) {
                    selectedOption = null
                    isCorrect = false
                }

                // Question Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentWord.english,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp
                            ),
                            color = DeepGreen,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Select the correct translation",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // Options
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    options.forEach { option ->
                        val isThisOptionCorrect = option == currentWord.translation
                        
                        QuizOptionButton(
                            text = option,
                            isSelected = selectedOption == option,
                            isCorrectAnswer = isThisOptionCorrect,
                            isAnswerRevealed = selectedOption != null,
                            onClick = {
                                if (!isCorrect) { 
                                    selectedOption = option
                                    if (isThisOptionCorrect) {
                                        isCorrect = true
                                    }
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                if (isCorrect) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val buttonText = if (reviewMode == ReviewMode.LEARNING) "I KNOW IT" else "NEXT"
                        
                        Button(
                            onClick = {
                                if (reviewMode == ReviewMode.LEARNING) {
                                    viewModel.updateWord(currentWord.copy(known = true))
                                    viewModel.incrementDailyProgress()
                                }
                                if (currentIndex < wordsToReview.size - 1) {
                                    currentIndex++
                                } else {
                                    onBackClick()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DeepGreen),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(buttonText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else if (selectedOption != null && !isCorrect) {
                     Text(
                        text = "Try again!",
                        color = ErrorRed,
                        style = MaterialTheme.typography.titleMedium
                     )
                }

            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "All caught up!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = DeepGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No new words to review.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = onBackClick,
                        colors = ButtonDefaults.buttonColors(containerColor = DeepGreen),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(56.dp)
                    ) {
                        Text("Go Back")
                    }
                }
            }
        }
    }
}
