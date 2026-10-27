package com.lifeos.app.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeos.app.core.designsystem.theme.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LifeOSCircularProgressRing(
    modifier: Modifier = Modifier,
    percentage: Float, // 0.0f to 1.0f
    ringSize: Dp = 150.dp,
    strokeWidth: Dp = 12.dp,
    label: String = "Project Completion",
    accentColor: Color = LifeOSPrimaryAccent,
    secondaryColor: Color = LifeOSPrimaryAccentDark
) {
    val animatedProgress by animateFloatAsState(
        targetValue = percentage.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 900),
        label = "progress_anim"
    )

    Box(
        modifier = modifier.size(ringSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(strokeWidth / 2)) {
            val sweepAngle = animatedProgress * 360f
            val arcSize = size

            // Background Track
            drawArc(
                color = LifeOSBorder,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // Progress Arc with Gradient
            if (sweepAngle > 0f) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(secondaryColor, accentColor, accentColor)
                    ),
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )

                // Glowing Head Dot
                val angleRad = Math.toRadians((sweepAngle - 90.0)).toFloat()
                val radius = arcSize.width / 2
                val centerX = arcSize.width / 2
                val centerY = arcSize.height / 2
                val dotX = centerX + radius * cos(angleRad)
                val dotY = centerY + radius * sin(angleRad)

                drawCircle(
                    color = Color.White,
                    radius = (strokeWidth / 2.5f).toPx(),
                    center = Offset(dotX, dotY)
                )
            }
        }

        // Center Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                style = LifeOSTypography.displayLarge.copy(fontSize = 28.sp),
                color = LifeOSTextPrimary
            )
            Text(
                text = label,
                style = LifeOSTypography.bodySmall,
                color = LifeOSTextSecondary
            )
        }
    }
}

@Composable
fun LifeOSProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = LifeOSPrimaryAccent,
    trackColor: Color = LifeOSBorder
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 600),
        label = "linear_progress_anim"
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(CircleShape)
                .background(color)
        )
    }
}
