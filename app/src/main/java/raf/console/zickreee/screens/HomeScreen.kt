package raf.console.zickreee.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.Position
import raf.console.zickreee.components.StaticItem

@Composable
fun HomeScreen() {
    val items = listOf(
        "99 имен Аллаха",
        "Дуа из Корана",
        "Салаваты",
        "Истигфары",
        "Дуа для богатства",
        "Утренние и вечерние азкары",
        "Дуа исмуль А'зам",
        "72 дуа Пророка Мухаммада Салля Ллаху алейхи уа Саллям",
        "О приложении"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 64.dp)
    ) {
        items(items.size) { index ->
            StaticItem(
                title = items[index],
                position = when (index) {
                    0 -> Position.TOP
                    items.size - 1 -> Position.BOTTOM
                    else -> Position.CENTER
                }
            )
            Spacer(modifier = Modifier.height(8.dp)) // Отступ между карточками
        }
    }
}