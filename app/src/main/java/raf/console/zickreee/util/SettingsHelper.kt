package raf.console.zickreee.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberDuaDisplaySettings(): DuaDisplaySettings {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }

    // Создаем состояния для всех настроек
    var showArabic by remember { mutableStateOf(settingsManager.showArabic) }
    var showTranscription by remember { mutableStateOf(settingsManager.showTranscription) }
    var showTranslation by remember { mutableStateOf(settingsManager.showTranslation) }
    var showInfo by remember { mutableStateOf(settingsManager.showInfo) }
    var arabicTextSize by remember { mutableStateOf(settingsManager.arabicTextSize) }
    var transcriptionTextSize by remember { mutableStateOf(settingsManager.transcriptionTextSize) }
    var translationTextSize by remember { mutableStateOf(settingsManager.translationTextSize) }

    // Подписываемся на изменения в SharedPreferences
    LaunchedEffect(settingsManager) {
        snapshotFlow { settingsManager.showArabic }.collect { showArabic = it }
        snapshotFlow { settingsManager.showTranscription }.collect { showTranscription = it }
        snapshotFlow { settingsManager.showTranslation }.collect { showTranslation = it }
        snapshotFlow { settingsManager.showInfo }.collect { showInfo = it }
        snapshotFlow { settingsManager.arabicTextSize }.collect { arabicTextSize = it }
        snapshotFlow { settingsManager.transcriptionTextSize }.collect { transcriptionTextSize = it }
        snapshotFlow { settingsManager.translationTextSize }.collect { translationTextSize = it }
    }

    return DuaDisplaySettings(
        showArabic = showArabic,
        showTranscription = showTranscription,
        showTranslation = showTranslation,
        showInfo = showInfo,
        arabicTextSize = arabicTextSize,
        transcriptionTextSize = transcriptionTextSize,
        translationTextSize = translationTextSize
    )
}

data class DuaDisplaySettings(
    val showArabic: Boolean,
    val showTranscription: Boolean,
    val showTranslation: Boolean,
    val showInfo: Boolean,
    val arabicTextSize: Float,
    val transcriptionTextSize: Float,
    val translationTextSize: Float
)