package raf.console.zickreee.presentation.screens.dalailhairat

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.Position
import raf.console.zickreee.util.BookmarkManager
import raf.console.zickreee.domain.models.DuaItem
import raf.console.zickreee.util.VKBannerAd
import raf.console.zickreee.util.loadDuasFromAssets


@Composable
fun DuaIstigfarScreen(
    context: Context,
    onHomeClick: () -> Unit,
    bookmarkManager: BookmarkManager
) {
    val duas = remember { loadDuasFromAssets(context, "dua_istigfar.json") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp) // Горизонтальные отступы для всего экрана
        ) {
            // Заголовок с отступом сверху и выравниванием по центру
            item {
                Text(
                    text = "Дуа Истигфар (дуа о прощении грехов)",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center // Выравнивание по центру
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 16.dp) // Отступ сверху и снизу
                )
            }

            item {
                VKBannerAd(1806139)
            }

            // Список карточек
            items(duas) { dua ->
                DuaItem(
                    arabicDua = dua.arabic_dua,
                    transcript = dua.transcript,
                    translate = dua.translate,
                    position = if (duas.indexOf(dua) == 0) Position.TOP
                    else if (duas.indexOf(dua) == duas.size - 1) Position.BOTTOM
                    else Position.CENTER,
                    bookmarkManager = bookmarkManager
                )
                Spacer(modifier = Modifier.height(8.dp)) // Отступ между карточками
            }

            item {
                VKBannerAd(1806142)
            }
        }

        // Кнопка "На главную" в правом верхнем углу
        IconButton(
            onClick = onHomeClick, // Обработчик нажатия
            modifier = Modifier
                .align(Alignment.TopEnd) // Выравнивание в верхний правый угол
                .padding(24.dp) // Отступ от краев
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Home, // Иконка "Дом"
                contentDescription = "На главную",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
