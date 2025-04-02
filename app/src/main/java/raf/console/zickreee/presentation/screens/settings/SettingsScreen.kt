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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
    onHomeClick: () -> Unit,
    appViewModel: AppViewModel = viewModel(factory = AppViewModelFactory(LocalContext.current))
) {
    val context = LocalContext.current
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

    var showArabicSizeDialog by remember { mutableStateOf(false) }
    var showTranscriptionSizeDialog by remember { mutableStateOf(false) }
    var showTranslationSizeDialog by remember { mutableStateOf(false) }
    var tempTextSize by remember { mutableStateOf("") }

    // Диалог для ввода размера текста
    @Composable
    fun showTextSizeDialog(currentSize: Float, onConfirm: (Float) -> Unit) {
        tempTextSize = currentSize.toInt().toString()
        AlertDialog(
            onDismissRequest = {
                when {
                    showArabicSizeDialog -> showArabicSizeDialog = false
                    showTranscriptionSizeDialog -> showTranscriptionSizeDialog = false
                    showTranslationSizeDialog -> showTranslationSizeDialog = false
                }
            },
            title = { Text("Enter text size") },
            text = {
                TextField(
                    value = tempTextSize,
                    onValueChange = {
                        if (it.matches(Regex("^\\d{1,2}\$")) || it.isEmpty()) {
                            tempTextSize = it
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text("Size (12-70)") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val size = tempTextSize.toIntOrNull()?.toFloat() ?: currentSize
                        when {
                            showArabicSizeDialog -> {
                                if (size in 12f..70f) {
                                    onConfirm(size)
                                    showArabicSizeDialog = false
                                }
                            }
                            showTranscriptionSizeDialog -> {
                                if (size in 12f..54f) {
                                    onConfirm(size)
                                    showTranscriptionSizeDialog = false
                                }
                            }
                            showTranslationSizeDialog -> {
                                if (size in 12f..54f) {
                                    onConfirm(size)
                                    showTranslationSizeDialog = false
                                }
                            }
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        when {
                            showArabicSizeDialog -> showArabicSizeDialog = false
                            showTranscriptionSizeDialog -> showTranscriptionSizeDialog = false
                            showTranslationSizeDialog -> showTranslationSizeDialog = false
                        }
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            item {
                Text(
                    text = "General Settings",
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
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
                        onCheckedChange = { appViewModel.updateDynamicColor(it) }
                    )
                }
            }

            item {
                Text(
                    text = "Theme Settings",
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            item {
                ThemeOptionItem(
                    icon = Icons.Default.SystemUpdate,
                    title = "System Default",
                    selected = theme == ThemeOption.SystemDefault,
                    onClick = { appViewModel.updateTheme(ThemeOption.SystemDefault) }
                )
            }

            item {
                ThemeOptionItem(
                    icon = Icons.Outlined.WbSunny,
                    title = "Light Theme",
                    selected = theme == ThemeOption.Light,
                    onClick = { appViewModel.updateTheme(ThemeOption.Light) }
                )
            }

            item {
                ThemeOptionItem(
                    icon = Icons.Outlined.DarkMode,
                    title = "Dark Theme",
                    selected = theme == ThemeOption.Dark,
                    onClick = { appViewModel.updateTheme(ThemeOption.Dark) }
                )
            }

            item {
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
                        onCheckedChange = { appViewModel.updateContrastTheme(if (it) ContrastLevel.High else ContrastLevel.Default) }
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
                            if (!it) appViewModel.updateShowInfo(false)
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
                        checked = showInfo && showTranslation,
                        onCheckedChange = {
                            if (showTranslation) appViewModel.updateShowInfo(it)
                        },
                        enabled = showTranslation
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
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            // Арабский текст
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { showArabicSizeDialog = true }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.FontDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Arabic Text Size",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${arabicTextSize.toInt()}sp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Slider(
                        value = arabicTextSize,
                        onValueChange = { appViewModel.updateArabicTextSize(it) },
                        valueRange = 12f..70f,
                        steps = 58,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Транскрипция
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { showTranscriptionSizeDialog = true }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.FontDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Transcription Size",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${transcriptionTextSize.toInt()}sp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Slider(
                        value = transcriptionTextSize,
                        onValueChange = { appViewModel.updateTranscriptionTextSize(it) },
                        valueRange = 12f..54f,
                        steps = 42,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Перевод
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { showTranslationSizeDialog = true }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.FontDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Translation & Info Size",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${translationTextSize.toInt()}sp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Slider(
                        value = translationTextSize,
                        onValueChange = { appViewModel.updateTranslationTextSize(it) },
                        valueRange = 12f..54f,
                        steps = 42,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        IconButton(
            onClick = onHomeClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
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

        // Диалоги для ввода размера текста
        if (showArabicSizeDialog) {
            showTextSizeDialog(arabicTextSize) { size ->
                appViewModel.updateArabicTextSize(size)
            }
        }
        if (showTranscriptionSizeDialog) {
            showTextSizeDialog(transcriptionTextSize) { size ->
                appViewModel.updateTranscriptionTextSize(size)
            }
        }
        if (showTranslationSizeDialog) {
            showTextSizeDialog(translationTextSize) { size ->
                appViewModel.updateTranslationTextSize(size)
            }
        }
    }
}

@Composable
fun ThemeOptionItem(
    icon: ImageVector,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}