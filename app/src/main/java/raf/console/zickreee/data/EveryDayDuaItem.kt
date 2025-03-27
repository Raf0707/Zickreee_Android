package raf.console.zickreee.data


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.ExpandingTransition
import raf.console.zickreee.components.Position


@Composable
fun EveryDayDuaItem(
    day: String,
    arabicDua: String,
    transcript: String,
    translate: String,
    position: Position
) {
    val showDescription = remember { mutableStateOf(false) }

    // Увеличиваем длительность анимации до 600 мс и добавляем плавный эффект
    val animatedArrowRotation = animateFloatAsState(
        targetValue = if (showDescription.value) 0f else -180f,
        animationSpec = tween(
            durationMillis = 600, // Увеличиваем длительность анимации
            easing = LinearOutSlowInEasing // Плавный эффект
        )
    )

    // Анимация изменения цвета фона
    val animatedBackgroundColor = animateColorAsState(
        targetValue = if (showDescription.value) {
            MaterialTheme.colorScheme.surfaceContainerHigh
        } else MaterialTheme.colorScheme.surfaceContainerLow,
        animationSpec = tween(
            durationMillis = 600, // Увеличиваем длительность анимации
            easing = LinearOutSlowInEasing // Плавный эффект
        )
    )

    // Анимация изменения цвета контейнера стрелочки
    val animatedArrowContainerColor = animateColorAsState(
        targetValue = if (showDescription.value) {
            MaterialTheme.colorScheme.surfaceContainerHighest
        } else MaterialTheme.colorScheme.surfaceContainer,
        animationSpec = tween(
            durationMillis = 600, // Увеличиваем длительность анимации
            easing = LinearOutSlowInEasing // Плавный эффект
        )
    )

    val extraLargeShape = MaterialTheme.shapes.extraLarge
    val shape = remember(position) {
        when (position) {
            Position.TOP -> extraLargeShape.copy(
                bottomStart = CornerSize(12.dp),
                bottomEnd = CornerSize(12.dp)
            )
            Position.CENTER -> RoundedCornerShape(12.dp)
            Position.SOLO -> extraLargeShape
            Position.BOTTOM -> extraLargeShape.copy(
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
        // Заголовок дня недели (по центру)
        Text(
            text = day,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp) // Отступ снизу
        )

        // Арабский текст
        Text(
            text = arabicDua,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Используем ExpandingTransition для плавного раскрытия
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

        // Стрелочка (остаётся внизу по центру)
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