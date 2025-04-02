package raf.console.zickreee.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import raf.console.zickreee.R
import raf.console.zickreee.ui.theme.ContrastLevel
import raf.console.zickreee.util.ThemeOption
import raf.console.zickreee.presentation.viewmodel.AppViewModel
import raf.console.zickreee.presentation.viewmodel.AppViewModelFactory


@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit, // Колбэк для нажатия на кнопку "На главную"
    appViewModel: AppViewModel = viewModel(factory = AppViewModelFactory(LocalContext.current))
) {
    val context = LocalContext.current

    // Подписываемся на состояния из ViewModel
    val theme by appViewModel.theme.collectAsState()
    val dynamicColor by appViewModel.dynamicColor.collectAsState()
    val contrastTheme by appViewModel.contrastTheme.collectAsState()

    val showArabic by appViewModel.showArabic.collectAsState()
    val showTranscription by appViewModel.showTranscription.collectAsState()
    val showTranslation by appViewModel.showTranslation.collectAsState()
    val showInfo by appViewModel.showInfo.collectAsState()

    val arabicTextSize by appViewModel.arabicTextSize.collectAsState()
    val transcriptionTextSize by appViewModel.transcriptionTextSize.collectAsState()
    val translationTextSize by appViewModel.translationTextSize.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            // Верхний блок с кнопкой "Назад" и заголовком
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            item {
                // Общие настройки
                Text(
                    text = "General Settings",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            item {
                // Опция динамических цветов
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Dynamic Colors", fontSize = 18.sp)
                        Text(
                            text = if (dynamicColor) "Dynamic colors are currently enabled." else "Dynamic colors are currently disabled.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Switch(
                        checked = dynamicColor,
                        onCheckedChange = { newValue ->
                            appViewModel.updateDynamicColor(newValue)
                        }
                    )
                }
            }

            item {
                // Настройки темы
                Text(
                    text = "Theme Settings",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            item {
                ThemeOptionItem(
                    title = "System Default",
                    selected = theme == ThemeOption.SystemDefault,
                    onClick = {
                        appViewModel.updateTheme(ThemeOption.SystemDefault)
                    }
                )
            }

            item {
                ThemeOptionItem(
                    title = "Light Theme",
                    selected = theme == ThemeOption.Light,
                    onClick = {
                        appViewModel.updateTheme(ThemeOption.Light)
                    }
                )
            }

            item {
                ThemeOptionItem(
                    title = "Dark Theme",
                    selected = theme == ThemeOption.Dark,
                    onClick = {
                        appViewModel.updateTheme(ThemeOption.Dark)
                    }
                )
            }

            item {
                // Опция контрастной темы
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.ColorLens, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "High Contrast Theme", fontSize = 18.sp)
                    }
                    Switch(
                        checked = contrastTheme == ContrastLevel.High,
                        onCheckedChange = { newValue ->
                            appViewModel.updateContrastTheme(if (newValue) ContrastLevel.High else ContrastLevel.Default)
                        }
                    )
                }
            }

            item {
                Text(
                    text = "Text Display Settings",
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = showArabic,
                        onCheckedChange = { appViewModel.updateShowArabic(it) }
                    )
                    Text(
                        text = "Show Arabic text",
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = showTranscription,
                        onCheckedChange = { appViewModel.updateShowTranscription(it) }
                    )
                    Text(
                        text = "Show transcription",
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = showTranslation,
                        onCheckedChange = {
                            appViewModel.updateShowTranslation(it)
                            if (!it) appViewModel.updateShowInfo(false) // Автоматически скрываем info
                        }
                    )
                    Text(
                        text = "Show translation",
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = showInfo && showTranslation, // Зависит от перевода
                        onCheckedChange = {
                            if (showTranslation) { // Можно изменить только если включен перевод
                                appViewModel.updateShowInfo(it)
                            }
                        },
                        enabled = showTranslation // Блокируем если перевод выключен
                    )
                    Text(
                        text = "Show additional info",
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (showTranslation) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            item {
                Text(
                    text = "Text Size Settings",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            // Слайдер для арабского текста
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.FontDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Arabic Text Size",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${arabicTextSize.toInt()}sp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Slider(
                        value = arabicTextSize,
                        onValueChange = { appViewModel.updateArabicTextSize(it) },
                        valueRange = 12f..30f,
                        steps = 8,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Слайдер для транскрипции
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.FontDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Transcription Size",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${transcriptionTextSize.toInt()}sp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Slider(
                        value = transcriptionTextSize,
                        onValueChange = { appViewModel.updateTranscriptionTextSize(it) },
                        valueRange = 12f..24f,
                        steps = 6,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Слайдер для перевода и информации
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.FontDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Translation & Info Size",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${translationTextSize.toInt()}sp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Slider(
                        value = translationTextSize,
                        onValueChange = { appViewModel.updateTranslationTextSize(it) },
                        valueRange = 12f..24f,
                        steps = 6,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

        }

        // Кнопка "На главную" в правом верхнем углу
        IconButton(
            onClick = onHomeClick, // Обработчик нажатия
            modifier = Modifier
                .align(Alignment.TopEnd) // Выравнивание в верхний правый угол
                .padding(32.dp) // Отступ от краев
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


@Composable
fun ThemeOptionItem(title: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = when (title) {
                    "System Default" -> "Use system default theme"
                    "Light Theme" -> "Use light theme"
                    else -> "Use night theme"
                },
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}