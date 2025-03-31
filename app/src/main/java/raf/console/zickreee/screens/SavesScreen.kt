package raf.console.zickreee.screens


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.data.DuaItem
import raf.console.zickreee.components.Position
import raf.console.zickreee.data.BookmarkManager
import raf.console.zickreee.util.loadDuasFromAssets

@Composable
fun SavesScreen(
    context: Context,
    onHomeClick: () -> Unit,
    bookmarkManager: BookmarkManager
) {
    val bookmarks by bookmarkManager.getBookmarksFlow().collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            if (bookmarks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет сохранённых дуа",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    items(bookmarks) { bookmark ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                // Арабский текст
                                Text(
                                    text = bookmark.arabicDua,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        textAlign = TextAlign.End
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Транскрипция
                                Text(
                                    text = bookmark.transcript,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Перевод
                                Text(
                                    text = bookmark.translate,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                // Дополнительная информация (если есть)
                                bookmark.additionalInfo?.let { info ->
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = info,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Кнопка удаления из закладок
                                Button(
                                    onClick = { bookmarkManager.removeBookmark(bookmark.arabicDua) },
                                    modifier = Modifier.align(Alignment.End),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.error
                                    ),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text("Удалить")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Кнопка "На главную" в правом верхнем углу
        IconButton(
            onClick = onHomeClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "На главную",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}