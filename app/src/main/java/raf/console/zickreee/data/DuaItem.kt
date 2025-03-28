package raf.console.zickreee.data

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.KeyboardArrowUp
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import raf.console.zickreee.components.ExpandingTransition
import raf.console.zickreee.components.Field
import raf.console.zickreee.components.Position
import raf.console.zickreee.util.rememberDuaDisplaySettings
import java.io.File

// 1. Сначала создадим модель данных для закладок в новом файле data/Bookmark.kt
data class Bookmark(
    val day: String? = null,
    val arabicDua: String,
    val transcript: String,
    val translate: String,
    val additionalInfo: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// 2. Создадим класс для работы с файлом bookmarks.json в utils/BookmarkManager.kt
class BookmarkManager(private val context: Context) {
    private val bookmarksFile = "bookmarks.json"
    private val gson = Gson()
    private val bookmarksFlow = MutableStateFlow<List<Bookmark>>(emptyList())

    init {
        loadBookmarks()
    }

    private fun loadBookmarks() {
        try {
            val file = File(context.filesDir, bookmarksFile)
            if (file.exists()) {
                val jsonString = file.readText()
                val bookmarks = gson.fromJson<List<Bookmark>>(
                    jsonString,
                    object : TypeToken<List<Bookmark>>() {}.type
                ) ?: emptyList()
                bookmarksFlow.value = bookmarks
            }
        } catch (e: Exception) {
            Log.e("BookmarkManager", "Error loading bookmarks", e)
        }
    }

    fun getBookmarksFlow(): Flow<List<Bookmark>> = bookmarksFlow

    fun getAllBookmarks(): List<Bookmark> {
        return try {
            val jsonString = context.assets.open(bookmarksFile).bufferedReader().use { it.readText() }
            gson.fromJson(jsonString, object : TypeToken<List<Bookmark>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveBookmarks(bookmarks: List<Bookmark>) {
        try {
            val jsonString = gson.toJson(bookmarks)
            File(context.filesDir, bookmarksFile).writeText(jsonString)
            bookmarksFlow.value = bookmarks
        } catch (e: Exception) {
            Log.e("BookmarkManager", "Error saving bookmarks", e)
        }
    }

    fun addBookmark(bookmark: Bookmark) {
        val current = bookmarksFlow.value.toMutableList()
        if (!current.any { it.arabicDua == bookmark.arabicDua }) {
            current.add(bookmark)
            saveBookmarks(current)
        }
    }

    fun removeBookmark(arabicDua: String) {
        val current = bookmarksFlow.value.toMutableList()
        current.removeAll { it.arabicDua == arabicDua }
        saveBookmarks(current)
    }

    fun isBookmarked(arabicDua: String): Boolean {
        return bookmarksFlow.value.any { it.arabicDua == arabicDua }
    }
}

// 3. Обновленный DuaItem с кнопкой закладки
@Composable
fun DuaItem(
    day: String? = null,
    arabicDua: String,
    transcript: String,
    translate: String,
    additionalInfo: String? = null,
    position: Position,
    bookmarkManager: BookmarkManager,
    showArabic: Boolean = rememberDuaDisplaySettings().showArabic,
    showTranscription: Boolean = rememberDuaDisplaySettings().showTranscription,
    showTranslation: Boolean = rememberDuaDisplaySettings().showTranslation,
    showInfo: Boolean = rememberDuaDisplaySettings().showInfo
) {
    // Определяем видимые элементы в правильном порядке
    val visibleFields = buildList {
        if (showArabic && arabicDua.isNotBlank()) add(Field.ARABIC to arabicDua)
        if (showTranscription && transcript.isNotBlank()) add(Field.TRANSCRIPT to transcript)
        if (showTranslation && translate.isNotBlank()) add(Field.TRANSLATE to translate)
        if (showInfo && additionalInfo?.isNotBlank() == true) add(Field.INFO to additionalInfo)
    }

    // Стрелка показывается только если есть что раскрывать (более 1 элемента)
    val showArrow = visibleFields.size > 1
    val showDescription = remember { mutableStateOf(!showArrow) } // Если один элемент - сразу раскрыт

    val context = LocalContext.current
    val bookmarks by bookmarkManager.getBookmarksFlow().collectAsState(initial = emptyList())
    val isBookmarked by remember(bookmarks, arabicDua) {
        derivedStateOf { bookmarks.any { it.arabicDua == arabicDua } }
    }

    // Анимации
    val animatedArrowRotation = animateFloatAsState(
        targetValue = if (showDescription.value) 0f else -180f,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
    )

    val animatedBackgroundColor = animateColorAsState(
        targetValue = if (showDescription.value) MaterialTheme.colorScheme.surfaceContainerHigh
        else MaterialTheme.colorScheme.surfaceContainerLow,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
    )

    val shapes = MaterialTheme.shapes

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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(animatedBackgroundColor.value)
            .clickable(enabled = showArrow) { showDescription.value = !showDescription.value }
            .padding(16.dp)
    ) {
        // Верхняя часть с кнопками и первым видимым полем
        Column(modifier = Modifier.fillMaxWidth()) {
            // Ряд кнопок
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Кнопка закладки
                IconButton(
                    onClick = {
                        if (isBookmarked) {
                            bookmarkManager.removeBookmark(arabicDua)
                        } else {
                            bookmarkManager.addBookmark(
                                Bookmark(
                                    arabicDua = arabicDua,
                                    transcript = transcript,
                                    translate = translate
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
                        val textToCopy = buildString {
                            if (showArabic) append("$arabicDua\n\n")
                            if (showTranscription) append("$transcript\n\n")
                            if (showTranslation) append(translate)
                            if (showInfo && additionalInfo != null) append("\n\n$additionalInfo")
                        }
                        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("dua_text", textToCopy.trim())
                        clipboardManager.setPrimaryClip(clip)
                        Toast.makeText(context, "Текст скопирован", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Копировать",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }

                // Кнопка поделиться
                IconButton(
                    onClick = {
                        val textToShare = buildString {
                            if (showArabic) append("$arabicDua\n\n")
                            if (showTranscription) append("$transcript\n\n")
                            if (showTranslation) append(translate)
                            if (showInfo && additionalInfo != null) append("\n\n$additionalInfo")
                        }
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, textToShare.trim())
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Поделиться"))
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Поделиться",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            // Первое видимое поле
            visibleFields.firstOrNull()?.let { (fieldType, text) ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = text,
                    color = when (fieldType) {
                        Field.ARABIC -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    style = when (fieldType) {
                        Field.ARABIC -> MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                        else -> MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Раскрывающаяся часть (остальные поля)
        if (visibleFields.size > 1) {
            ExpandingTransition(
                visible = showDescription.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    visibleFields.drop(1).forEach { (fieldType, text) ->
                        Text(
                            text = text,
                            color = when (fieldType) {
                                Field.ARABIC -> MaterialTheme.colorScheme.onSurface
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            style = when (fieldType) {
                                Field.ARABIC -> MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                                else -> MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Стрелка (только если есть что раскрывать)
        if (showArrow) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowUp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .rotate(animatedArrowRotation.value)
                    .size(24.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
// 4. Пример использования в родительском компоненте:
/*@Composable
fun DuaListScreen() {
    val context = LocalContext.current
    val bookmarkManager = remember { BookmarkManager(context) }

    LazyColumn {
        items(duas) { dua ->
            DuaItem(
                arabicDua = dua.arabic,
                transcript = dua.transcript,
                translate = dua.translate,
                position = Position.CENTER,
                bookmarkManager = bookmarkManager
            )
        }
    }
}*/