package raf.console.zickreee.screens.dalailhairat

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import raf.console.zickreee.components.Position
import raf.console.zickreee.data.StaticItem

@Composable
fun DalailHomeScreen(navController: NavController) {
    val items = listOf(
        "На главную" to "back",
        "Как читать" to "howToRead",
        "Важное дуа из Даляиль-аль-Хайрат" to "importantDua",
        "Дуа Истигфар (дуа о прощении грехов)" to "duaIstigfar",
        "Дуа Ният (дуа намерения)" to "duaNiyat",
        "Дуа Фатих (или дуа ифтитах - дуа открытия)" to "duaFatih",
        "Асмауль Хусна (99 имен Аллаха)" to "asmaUlHusna",
        "Дуа Тауфик (Дуа о помощи) " to "duaTaufick",
        "Имена Пророка Мухаммада Салля Ллаху алейхи уа Саллям" to "namesRasul",
        "Понедельник" to "mon",
        "Вторник" to "tus",
        "Среда" to "wen",
        "Четверг" to "ths",
        "Пятница" to "fri",
        "Суббота" to "sat",
        "Воскресенье" to "sun",
        "Понедельник 2" to "mon2",
        "Дуа Хатм (Дуа завершения)" to "duaHatm"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 64.dp)
    ) {
        items(items.size) { index ->
            val (title, route) = items[index]
            StaticItem(
                title = title,
                position = when (index) {
                    0 -> Position.TOP
                    items.size - 1 -> Position.BOTTOM
                    else -> Position.CENTER
                },
                onClick = {
                    navController.navigate(route) // Навигация на соответствующий экран
                }
            )
            Spacer(modifier = Modifier.height(8.dp)) // Отступ между карточками
        }
    }
}

