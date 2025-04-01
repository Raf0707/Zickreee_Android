package raf.console.zickreee.presentation.screens.home

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
import raf.console.zickreee.domain.models.StaticItem

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Элементы меню в Drawer
    val drawerItems = listOf(
        DrawerItem("Дуа", Icons.Default.List, "home"),
        DrawerItem("Счетчик", Icons.Default.Refresh, "counter"),
        DrawerItem("Сохраненки", Icons.Default.Bookmark, "saves"),
        DrawerItem("Настройки", Icons.Default.Settings, "settings"),
        DrawerItem("О приложении", Icons.Default.Info, "aboutApp")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(drawerItems, navController) {
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Главная") },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Меню",
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Остальной код остается без изменений
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
                "Счетчик" to "counter",
                "Сохраненки" to "saves",
                "Настройки" to "settings",
                "О приложении" to "aboutApp"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
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
                            navController.navigate(route)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

data class DrawerItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun DrawerContent(
    items: List<DrawerItem>,
    navController: NavController,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 32.dp, top = 256.dp, bottom = 2.dp) // Убираем горизонтальные отступы у Column
    ) {

        // Элементы меню без боковых отступов
        items.forEachIndexed { index, item ->
            Column {
                NavigationDrawerItem(
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(item.title) },
                    selected = false,
                    onClick = {
                        navController.navigate(item.route)
                        onItemClick()
                    },
                    modifier = Modifier.fillMaxWidth() // Растягиваем на всю ширину
                )

                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}