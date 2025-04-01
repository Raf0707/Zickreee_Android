package raf.console.zickreee.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import raf.console.zickreee.ui.theme.ContrastLevel
import raf.console.zickreee.util.SettingsManager
import raf.console.zickreee.util.ThemeOption

class AppViewModel(private val settingsManager: SettingsManager) : ViewModel() {
    // Состояния для темы, динамических цветов и контраста
    private val _theme = MutableStateFlow(settingsManager.selectedTheme)
    val theme: StateFlow<ThemeOption> = _theme.asStateFlow()

    private val _dynamicColor = MutableStateFlow(settingsManager.isDynamicColorsEnabled)
    val dynamicColor: StateFlow<Boolean> = _dynamicColor.asStateFlow()

    private val _contrastTheme = MutableStateFlow(settingsManager.contrastLevel)
    val contrastTheme: StateFlow<ContrastLevel> = _contrastTheme.asStateFlow()

    private val _showArabic = MutableStateFlow(settingsManager.showArabic)
    val showArabic: StateFlow<Boolean> = _showArabic.asStateFlow()

    private val _showTranscription = MutableStateFlow(settingsManager.showTranscription)
    val showTranscription: StateFlow<Boolean> = _showTranscription.asStateFlow()

    private val _showTranslation = MutableStateFlow(settingsManager.showTranslation)
    val showTranslation: StateFlow<Boolean> = _showTranslation.asStateFlow()

    private val _showInfo = MutableStateFlow(settingsManager.showInfo)
    val showInfo: StateFlow<Boolean> = _showInfo.asStateFlow()

    // Функции для обновления состояний
    fun updateTheme(newTheme: ThemeOption) {
        _theme.value = newTheme
        settingsManager.selectedTheme = newTheme
    }

    fun updateDynamicColor(enabled: Boolean) {
        _dynamicColor.value = enabled
        settingsManager.isDynamicColorsEnabled = enabled
    }

    fun updateContrastTheme(contrastLevel: ContrastLevel) {
        _contrastTheme.value = contrastLevel
        settingsManager.contrastLevel = contrastLevel
    }

    // Методы для обновления состояний
    fun updateShowArabic(show: Boolean) {
        _showArabic.value = show
        settingsManager.showArabic = show
    }

    fun updateShowTranscription(show: Boolean) {
        _showTranscription.value = show
        settingsManager.showTranscription = show
    }

    fun updateShowTranslation(show: Boolean) {
        _showTranslation.value = show
        settingsManager.showTranslation = show
        if (!show) {
            updateShowInfo(false) // Автоматически отключаем info при отключении перевода
        }
    }

    fun updateShowInfo(show: Boolean) {
        if (showTranslation.value) { // Можно изменить только если включен перевод
            _showInfo.value = show
            settingsManager.showInfo = show
        }
    }
}