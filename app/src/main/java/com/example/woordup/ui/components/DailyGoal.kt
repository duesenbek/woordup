package com.example.woordup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.ui.theme.DeepGreenLight

@Composable
fun DailyGoal(
    current: Int,
    goal: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (goal > 0) current.toFloat() / goal else 0f

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Daily Goal",
            style = MaterialTheme.typography.titleMedium,
            color = DeepGreen,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$current / $goal words learned",
            style = MaterialTheme.typography.bodyMedium,
            color = DeepGreenLight
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = DeepGreen,
            trackColor = Color.LightGray.copy(alpha = 0.3f),
        )
    }
}
