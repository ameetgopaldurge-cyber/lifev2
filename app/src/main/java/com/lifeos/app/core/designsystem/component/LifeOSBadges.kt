package com.lifeos.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun LifeOSPriorityBadge(
    priority: String, // "High", "Medium", "Low"
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (priority.lowercase()) {
        "high" -> Color(0xFF381E32) to Color(0xFFF472B6)
        "medium" -> Color(0xFF1E2E28) to Color(0xFF34D399)
        else -> Color(0xFF1A283B) to Color(0xFF60A5FA)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = priority,
            style = LifeOSTypography.labelSmall,
            color = textColor
        )
    }
}

@Composable
fun LifeOSTagBadge(
    text: String,
    color: Color = LifeOSPrimaryAccent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = LifeOSTypography.labelSmall,
            color = color
        )
    }
}

@Composable
fun LifeOSStatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val color = when (status.lowercase()) {
        "completed", "published", "paid" -> LifeOSSuccess
        "in progress", "editing", "active" -> LifeOSPrimaryAccent
        "scheduled", "pending", "proposal sent" -> LifeOSWarning
        "overdue", "draft" -> LifeOSError
        else -> LifeOSTextSecondary
    }

    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = status,
            style = LifeOSTypography.labelSmall,
            color = color
        )
    }
}
