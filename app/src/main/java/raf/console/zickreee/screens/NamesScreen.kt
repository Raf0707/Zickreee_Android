package raf.console.zickreee.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.NameItem
import raf.console.zickreee.components.Position
import raf.console.zickreee.data.Name

@Composable
fun NamesScreen(
    arabicNames: List<String>,
    transcriptNames: List<String>,
    translateNames: List<String>,
    infoNames: List<String>,
    onHomeClick: () -> Unit // Колбэк для нажатия на кнопку "На главную"
) {
    // Объединяем 4 списка в один список объектов Name
    val names = arabicNames
        .zip(transcriptNames) { arabic, transcript -> Pair(arabic, transcript) }
        .zip(translateNames) { (arabic, transcript), translate -> Triple(arabic, transcript, translate) }
        .zip(infoNames) { (arabic, transcript, translate), info ->
            Name(
                arabic_name = arabic,
                transcript_name = transcript,
                translate_name = translate,
                info_name = info
            )
        }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 64.dp)
        ) {
            item {
                Text(
                    text = "99 имен Аллаха",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center // Выравнивание по центру
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 32.dp) // Отступ сверху и снизу
                )
            }
            items(names.size) { index ->
                NameItem(
                    arabicName = names[index].arabic_name,
                    transcriptName = names[index].transcript_name,
                    translateName = names[index].translate_name,
                    infoName = names[index].info_name,
                    position = when (index) {
                        0 -> Position.TOP
                        names.size - 1 -> Position.BOTTOM
                        else -> Position.CENTER
                    }
                )
                Spacer(modifier = Modifier.height(8.dp)) // Отступ между карточками
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

        /*Text(
            "На главную",
            modifier = Modifier
                .align(Alignment.TopEnd) // Выравнивание в верхний правый угол
                .padding(top = 64.dp, end = 32.dp) // Отступ от краев
                .background(
                    color = MaterialTheme.colorScheme.surface,
                )
        )*/

    }
}