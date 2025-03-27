package raf.console.zickreee.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberDuaDisplaySettings(): DuaDisplaySettings {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }

    return remember(settingsManager) {
        DuaDisplaySettings(
            showArabic = settingsManager.showArabic,
            showTranscription = settingsManager.showTranscription,
            showTranslation = settingsManager.showTranslation,
            showInfo = settingsManager.showInfo
        )
    }
}

data class DuaDisplaySettings(
    val showArabic: Boolean,
    val showTranscription: Boolean,
    val showTranslation: Boolean,
    val showInfo: Boolean
)