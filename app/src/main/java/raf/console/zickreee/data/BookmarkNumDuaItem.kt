package raf.console.zickreee.data

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import raf.console.zickreee.components.ExpandingTransition
import raf.console.zickreee.components.Position

@Composable
fun BookmarkNumDuaItem(
    num: String,
    arabicText: String,
    transcript: String,
    translate: String,
    isBookmarked: Boolean,
    position: Position,
    //onBookmarkClick: () -> Unit
) {
    val showDescription = remember { mutableStateOf(false) }

    // Animation specs
    val animationSpec = tween<Float>(
        durationMillis = 600,
        easing = LinearOutSlowInEasing
    )

    val animatedArrowRotation = animateFloatAsState(
        targetValue = if (showDescription.value) 0f else -180f,
        animationSpec = animationSpec
    )

    val animatedBackgroundColor = animateColorAsState(
        targetValue = if (showDescription.value) {
            MaterialTheme.colorScheme.surfaceContainerHigh
        } else MaterialTheme.colorScheme.surfaceContainerLow,
        animationSpec = tween (
            durationMillis = 800,
            easing = LinearOutSlowInEasing
        )
    )

    val shapes = MaterialTheme.shapes
    val shape = remember(position) {
        when (position) {
            Position.TOP -> shapes.extraLarge.copy(
                bottomStart = CornerSize(12.dp),
                bottomEnd = CornerSize(12.dp)
            )
            Position.CENTER -> RoundedCornerShape(12.dp)
            Position.SOLO -> shapes.extraLarge
            Position.BOTTOM -> shapes.extraLarge.copy(
                topStart = CornerSize(12.dp),
                topEnd = CornerSize(12.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(animatedBackgroundColor.value)
            .clickable { showDescription.value = !showDescription.value }
            .padding(16.dp)
    ) {
        // Number and Bookmark row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = num,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            IconButton(
                onClick = {},//onBookmarkClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Arabic text
        Text(
            text = arabicText,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Expandable content
        ExpandingTransition(
            visible = showDescription.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = transcript,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = translate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Arrow indicator
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowUp,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .rotate(animatedArrowRotation.value)
                .size(24.dp),
            contentDescription = null
        )
    }
}