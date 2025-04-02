package raf.console.zickreee.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import raf.console.zickreee.ui.theme.ContrastLevel

//import raf.console.zickreee.ui.theme.ContrastLevel

class SettingsManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    // Сохранение и загрузка темы
    var selectedTheme: ThemeOption
        get() = ThemeOption.valueOf(
            sharedPreferences.getString("selected_theme", ThemeOption.SystemDefault.name) ?: ThemeOption.SystemDefault.name
        )
        set(value) {
            sharedPreferences.edit().putString("selected_theme", value.name).apply()
        }

    // Сохранение и загрузка динамических цветов
    var isDynamicColorsEnabled: Boolean
        get() = sharedPreferences.getBoolean("dynamic_colors_enabled", false)
        set(value) {
            sharedPreferences.edit().putBoolean("dynamic_colors_enabled", value).apply()
        }

    // Сохранение и загрузка уровня контраста
    var contrastLevel: ContrastLevel
        get() = ContrastLevel.valueOf(
            sharedPreferences.getString("contrast_level", ContrastLevel.Default.name) ?: ContrastLevel.Default.name
        )
        set(value) {
            sharedPreferences.edit().putString("contrast_level", value.name).apply()
        }

    var showArabic: Boolean
        get() = sharedPreferences.getBoolean("show_arabic", true)
        set(value) {
            sharedPreferences.edit().putBoolean("show_arabic", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    var showTranscription: Boolean
        get() = sharedPreferences.getBoolean("show_transcription", true)
        set(value) {
            sharedPreferences.edit().putBoolean("show_transcription", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    var showTranslation: Boolean
        get() = sharedPreferences.getBoolean("show_translation", true)
        set(value) {
            sharedPreferences.edit().putBoolean("show_translation", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    var showInfo: Boolean
        get() = sharedPreferences.getBoolean("show_info", true)
        set(value) {
            sharedPreferences.edit().putBoolean("show_info", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    var arabicTextSize: Float
        get() = sharedPreferences.getFloat("arabic_text_size", 18f)
        set(value) {
            sharedPreferences.edit().putFloat("arabic_text_size", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    var transcriptionTextSize: Float
        get() = sharedPreferences.getFloat("transcription_text_size", 16f)
        set(value) {
            sharedPreferences.edit().putFloat("transcription_text_size", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    var translationTextSize: Float
        get() = sharedPreferences.getFloat("translation_text_size", 16f)
        set(value) {
            sharedPreferences.edit().putFloat("translation_text_size", value).apply()
            _settingsChanges.tryEmit(Unit)
        }

    // Flow для отслеживания изменений
    private val _settingsChanges = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val settingsChanges: Flow<Unit> = _settingsChanges
}