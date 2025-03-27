package raf.console.zickreee.screens

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
fun HomeScreen(navController: NavController) {
    val items = listOf(
        "99 имен Аллаха" to "namesOfAllah",
        "Дуа из Корана" to "duaFromQuran",
        "Салаваты" to "salawat",
        "Истигфары" to "istighfar",
        "Дуа для богатства" to "duaForReachness",
        "Утренние и вечерние азкары" to "morningEveningAzkar",
        "Дуа исмуль А'зам" to "ismulAzam",
        "72 дуа Пророка Мухаммада Салля Ллаху алейхи уа Саллям" to "duaRasul",
        "Дуа и зикры после намаза" to "afterNamaz",
        "33 аята защиты Аль-Хирз" to "hirz",
        "Дуа для защиты и безопасности" to "duaProtect",
        "Рукъя - лечение аятами Корана" to "rukia",
        "Дуа и зикры на каждый день" to "everyDay",
        "Дуа Ифтитах (Сунна)" to "duaIftitahSunna",
        "Дуа Сайф ас-Сагир" to "duaSeyfSagir",
        "Дуа Таджнама" to "duaTajnama",
        "Даляиль Аль-Хайрат" to "dalailAlKhairat",
        "Сохраненки" to "saves",
        "Настройки" to "settings",
        "О приложении" to "aboutApp"
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

