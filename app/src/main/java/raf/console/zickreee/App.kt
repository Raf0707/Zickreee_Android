package raf.console.zickreee

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import raf.console.zickreee.ui.theme.AppTheme
import raf.console.zickreee.util.SettingsManager
import raf.console.zickreee.util.ThemeOption

@Composable
fun MyApp() {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }

    AppTheme(
        darkTheme = when (settingsManager.selectedTheme) {
            ThemeOption.SystemDefault -> isSystemInDarkTheme()
            ThemeOption.Light -> false
            ThemeOption.Dark -> true
        },
        dynamicColor = settingsManager.isDynamicColorsEnabled,
        contrastLevel = settingsManager.contrastLevel
    ) {
        //AppNavigation()
    }
}

