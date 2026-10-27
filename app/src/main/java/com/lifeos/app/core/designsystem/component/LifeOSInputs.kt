package com.lifeos.app.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun LifeOSTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = LifeOSTypography.labelMedium,
                color = LifeOSTextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = LifeOSShapes.small,
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            isError = isError,
            placeholder = {
                if (placeholder != null) {
                    Text(text = placeholder, color = LifeOSTextTertiary)
                }
            },
            leadingIcon = if (leadingIcon != null) {
                { Icon(imageVector = leadingIcon, contentDescription = null, tint = LifeOSTextSecondary) }
            } else null,
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LifeOSCardBackground,
                unfocusedContainerColor = LifeOSCardBackground,
                disabledContainerColor = LifeOSBackground,
                errorContainerColor = LifeOSCardBackground,
                focusedBorderColor = LifeOSPrimaryAccent,
                unfocusedBorderColor = LifeOSBorder,
                errorBorderColor = LifeOSError,
                focusedTextColor = LifeOSTextPrimary,
                unfocusedTextColor = LifeOSTextPrimary
            )
        )
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = LifeOSTypography.bodySmall,
                color = LifeOSError
            )
        }
    }
}

@Composable
fun LifeOSFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
        shape = CircleShape,
        color = if (selected) LifeOSPrimaryAccent else LifeOSSecondarySurface,
        border = if (selected) null else BorderStroke(1.dp, LifeOSBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (selected) LifeOSBackground else LifeOSTextSecondary
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = LifeOSTypography.labelMedium,
                color = if (selected) LifeOSBackground else LifeOSTextPrimary
            )
        }
    }
}
