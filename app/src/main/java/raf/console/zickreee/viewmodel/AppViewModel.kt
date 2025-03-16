package raf.console.zickreee.viewmodel

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
}