package raf.console.zickreee.domain.models

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.ExpandingTransition
import raf.console.zickreee.components.Position
import raf.console.zickreee.util.BookmarkManager

@Composable
fun NameItem(
    arabicName: String,
    transcriptName: String,
    translateName: String,
    infoName: String,
    position: Position,
    bookmarkManager: BookmarkManager
) {
    val showDescription = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val shapes = MaterialTheme.shapes

    // Получаем состояние закладок
    val bookmarks by bookmarkManager.getBookmarksFlow().collectAsState(initial = emptyList())

    // Проверяем, есть ли текущее имя в закладках
    val isBookmarked by remember(bookmarks, arabicName) {
        derivedStateOf { bookmarks.any { it.arabicDua == arabicName } }
    }

    // Анимации
    val animatedArrowRotation = animateFloatAsState(
        targetValue = if (showDescription.value) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
    )

    val animatedBackgroundColor = animateColorAsState(
        targetValue = if (showDescription.value) {
            MaterialTheme.colorScheme.surfaceContainerHigh
        } else MaterialTheme.colorScheme.surfaceContainerLow,
        animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
    )

    val shape = remember(position, shapes) {
        when (position) {
            Position.TOP -> shapes.extraLarge.copy(
                bottomStart = CornerSize(12.dp),
                bottomEnd = CornerSize(12.dp))
            Position.CENTER -> RoundedCornerShape(12.dp)
            Position.SOLO -> shapes.extraLarge
            Position.BOTTOM -> shapes.extraLarge.copy(
                topStart = CornerSize(12.dp),
                topEnd = CornerSize(12.dp))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(animatedBackgroundColor.value)
            .clickable { showDescription.value = !showDescription.value }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Верхняя строка с кнопками
            Box(modifier = Modifier.fillMaxWidth()) {
                // Кнопки в правом верхнем углу
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .height(48.dp), // Фиксированная высота
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Кнопка закладки
                    IconButton(
                        onClick = {
                            if (isBookmarked) {
                                bookmarkManager.removeBookmark(arabicName)
                            } else {
                                bookmarkManager.addBookmark(
                                    Bookmark(
                                        arabicDua = arabicName,
                                        transcript = transcriptName,
                                        translate = translateName,
                                        additionalInfo = infoName // Сохраняем дополнительную информацию
                                    )
                                )
                            }
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark
                            else Icons.Outlined.Bookmark,
                            contentDescription = "Закладка",
                            tint = if (isBookmarked) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }

                    // Кнопка копирования
                    IconButton(
                        onClick = {
                            val textToCopy = "$arabicName\n\n$transcriptName\n\n$translateName\n\n$infoName"
                            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("name_text", textToCopy)
                            clipboardManager.setPrimaryClip(clip)
                            Toast.makeText(context, "Текст скопирован", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ContentCopy,
                            contentDescription = "Копировать",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Кнопка поделиться
                    IconButton(
                        onClick = {
                            val textToShare = "$arabicName\n\n$transcriptName\n\n$translateName\n\n$infoName"
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, textToShare)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Поделиться"))
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Поделиться",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Арабское имя с отступом
                Text(
                    text = arabicName,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp) // Увеличенный отступ сверху
                )
            }

            // Раскрывающаяся часть
            ExpandingTransition(
                visible = showDescription.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = transcriptName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = translateName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = infoName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Стрелочка внизу карточки
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .rotate(animatedArrowRotation.value)
                    .size(24.dp),
                contentDescription = null
            )
        }
    }
}