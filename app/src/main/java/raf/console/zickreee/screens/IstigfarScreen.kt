package raf.console.zickreee.screens


import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.DuaItem
import raf.console.zickreee.components.Position
import raf.console.zickreee.util.loadDuasFromAssets

@Composable
fun IstigfarScreen(context: Context) {
    val duas = remember { loadDuasFromAssets(context, "istigfar.json") }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp) // Горизонтальные отступы для всего экрана
    ) {
        // Заголовок с отступом сверху и выравниванием по центру
        item {
            Text(
                text = "Истигфар - мольба о прощении",
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

        // Список карточек

        items(duas) { dua ->
            DuaItem(
                arabicDua = dua.arabic_dua,
                transcript = dua.transcript,
                translate = dua.translate,
                position = if (duas.indexOf(dua) == 0) Position.TOP
                else if (duas.indexOf(dua) == duas.size - 1) Position.BOTTOM
                else Position.CENTER
            )
            Spacer(modifier = Modifier.height(8.dp)) // Отступ между карточками
        }

    }
}