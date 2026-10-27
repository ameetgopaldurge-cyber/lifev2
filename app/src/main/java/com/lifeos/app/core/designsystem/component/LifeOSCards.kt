package com.lifeos.app.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun LifeOSCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LifeOSCardBackground,
    borderColor: Color = LifeOSBorder,
    elevation: Dp = 0.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val clickableModifier = if (onClick != null) {
        modifier.clip(LifeOSShapes.medium).clickable { onClick() }
    } else {
        modifier.clip(LifeOSShapes.medium)
    }

    Surface(
        modifier = clickableModifier,
        shape = LifeOSShapes.medium,
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun LifeOSMetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color = LifeOSPrimaryAccent,
    iconBgColor: Color = iconTint.copy(alpha = 0.15f),
    badgeText: String? = null,
    badgeColor: Color = LifeOSSuccess,
    onClick: (() -> Unit)? = null
) {
    LifeOSCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            if (badgeText != null) {
                LifeOSTagBadge(text = badgeText, color = badgeColor)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = value,
            style = LifeOSTypography.titleLarge,
            color = LifeOSTextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            style = LifeOSTypography.bodyMedium,
            color = LifeOSTextSecondary
        )
    }
}

@Composable
fun LifeOSLifeAreaCard(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    icon: ImageVector,
    accentColor: Color,
    activeItemsCount: Int,
    completionPercentage: Int,
    onClick: () -> Unit
) {
    LifeOSCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(LifeOSShapes.small)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = name,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = LifeOSTypography.titleMedium,
                    color = LifeOSTextPrimary
                )
                Text(
                    text = description,
                    style = LifeOSTypography.bodySmall,
                    color = LifeOSTextSecondary,
                    maxLines = 1
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$completionPercentage%",
                    style = LifeOSTypography.labelLarge,
                    color = accentColor
                )
                Text(
                    text = "$activeItemsCount active",
                    style = LifeOSTypography.bodySmall,
                    color = LifeOSTextTertiary
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LifeOSProgressBar(
            progress = completionPercentage / 100f,
            color = accentColor,
            modifier = Modifier.fillMaxWidth().height(4.dp)
        )
    }
}
