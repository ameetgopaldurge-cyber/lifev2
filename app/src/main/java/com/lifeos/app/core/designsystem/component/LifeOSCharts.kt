package com.lifeos.app.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeos.app.core.designsystem.theme.*

data class BarChartItem(
    val label: String,
    val value: Float,
    val isHighlighted: Boolean = false
)

@Composable
fun LifeOSBarChart(
    items: List<BarChartItem>,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 140.dp,
    barColor: Color = LifeOSSecondarySurface,
    highlightColor: Color = LifeOSPrimaryAccent,
    labelColor: Color = LifeOSTextTertiary
) {
    if (items.isEmpty()) return

    val maxValue = items.maxOfOrNull { it.value }?.takeIf { it > 0 } ?: 1f

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val totalBars = items.size
                val availableWidth = size.width
                val barWidth = (availableWidth / totalBars) * 0.55f
                val spacing = (availableWidth / totalBars) * 0.45f

                items.forEachIndexed { index, item ->
                    val barHeight = (item.value / maxValue) * size.height
                    val left = index * (barWidth + spacing) + (spacing / 2)
                    val top = size.height - barHeight

                    // Bar
                    drawRoundRect(
                        color = if (item.isHighlighted) highlightColor else barColor,
                        topLeft = Offset(left, top),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(8f, 8f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEach { item ->
                Text(
                    text = item.label,
                    style = LifeOSTypography.bodySmall.copy(fontSize = 11.sp),
                    color = if (item.isHighlighted) LifeOSPrimaryAccent else labelColor
                )
            }
        }
    }
}
