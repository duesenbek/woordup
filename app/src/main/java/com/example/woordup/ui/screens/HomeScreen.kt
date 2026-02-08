package com.example.woordup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.woordup.ui.components.CircularProgressBar
import com.example.woordup.ui.components.WordCard
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.viewmodel.WordViewModel

@Composable
fun HomeScreen(
    viewModel: WordViewModel,
    onAddWordClick: () -> Unit,
    onReviewClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val words = uiState.words
    val dailyGoal by viewModel.dailyGoal.collectAsState()
    val dailyProgress by viewModel.dailyProgress.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressBar(
                current = dailyProgress,
                goal = dailyGoal,
                radius = 100.dp,
                strokeWidth = 16.dp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onReviewClick,
                colors = ButtonDefaults.buttonColors(containerColor = DeepGreen),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Start Learning",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Spacer(modifier = Modifier.height(48.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Recent words",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DeepGreen,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DeepGreen)
                }
            } else if (words.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No words yet. Add some to start learning!", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val recentWords = words.reversed().take(5)
                    
                    
                    items(recentWords.size) { index ->
                        val word = recentWords[index]
                        WordCard(
                            word = word,
                            onWordClick = { },
                            onKnownClick = { clickedWord ->
                                val updatedWord = clickedWord.copy(known = !clickedWord.known)
                                viewModel.updateWord(updatedWord)
                                if (!clickedWord.known) {
                                    viewModel.incrementDailyProgress()
                                }
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    
                    if (words.size > 5) {
                         item {
                            Text(
                                text = "View all in Vocabulary tab",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }

        // Quick Add Button (Secondary Flow)
        FloatingActionButton(
            onClick = onAddWordClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = DeepGreen,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Word")
        }
    }
}
