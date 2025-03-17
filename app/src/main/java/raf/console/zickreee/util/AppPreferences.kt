package raf.console.zickreee.util

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean("is_first_launch", true)
    }

    fun setFirstLaunchCompleted() {
        sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
    }
}