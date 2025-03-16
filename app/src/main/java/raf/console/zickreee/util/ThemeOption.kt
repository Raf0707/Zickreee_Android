package raf.console.zickreee.util

enum class ThemeOption {
    SystemDefault,
    Light,
    Dark;

    // Функция для получения строки, которую можно использовать для хранения в DataStore
    fun toThemeString(): String {
        return when (this) {
            SystemDefault -> "system"
            Light -> "light"
            Dark -> "dark"
        }
    }

    // Функция для получения соответствующего значения ThemeOption из строки
    companion object {
        fun fromThemeString(themeString: String): ThemeOption {
            return when (themeString) {
                "light" -> Light
                "dark" -> Dark
                else -> SystemDefault
            }
        }
    }
}
