package com.lifeos.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun LifeOSConfirmDialog(
    title: String,
    message: String,
    confirmText: String = "Delete",
    dismissText: String = "Cancel",
    isDestructive: Boolean = true,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = LifeOSCardBackground,
        title = {
            Text(
                text = title,
                style = LifeOSTypography.titleMedium,
                color = LifeOSTextPrimary
            )
        },
        text = {
            Text(
                text = message,
                style = LifeOSTypography.bodyMedium,
                color = LifeOSTextSecondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (isDestructive) LifeOSError else LifeOSPrimaryAccent
                )
            ) {
                Text(text = confirmText, style = LifeOSTypography.labelLarge)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissText, color = LifeOSTextSecondary)
            }
        }
    )
}
