package com.example.woordup.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.woordup.data.model.Word
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.ui.theme.DeepGreenLight
import com.example.woordup.ui.theme.White

@Composable
fun WordCard(
    word: Word,
    onWordClick: (Word) -> Unit,
    onKnownClick: (Word) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (word.known) DeepGreenLight.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 300),
        label = "cardBackground"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onWordClick(word) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = word.english,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = DeepGreen
                )
                Text(
                    text = word.translation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
            
            IconButton(onClick = { onKnownClick(word) }) {
                 Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Mark as Known",
                    tint = if (word.known) DeepGreen else Color.LightGray
                )
            }
        }
    }
}
