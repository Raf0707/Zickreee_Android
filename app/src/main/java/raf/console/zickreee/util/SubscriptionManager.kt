package raf.console.zickreee.util

import android.content.Context

class SubscriptionManager(val context: Context) {
    private val prefs = context.getSharedPreferences("subscription_prefs", Context.MODE_PRIVATE)

    // Типы подписок
    companion object {
        const val SUBSCRIPTION_NONE = 0
        const val SUBSCRIPTION_NO_ADS = 1
        const val SUBSCRIPTION_DONATION = 2
        const val SUBSCRIPTION_PREMIUM = 3
    }

    // Проверка активной подписки
    fun hasActiveSubscription(): Boolean {
        return getSubscriptionType() != SUBSCRIPTION_NONE
    }

    // Проверка конкретного типа подписки
    fun hasSubscription(type: Int): Boolean {
        return getSubscriptionType() == type
    }

    // Получение типа подписки
    fun getSubscriptionType(): Int {
        return prefs.getInt("subscription_type", SUBSCRIPTION_NONE)
    }

    // Установка подписки
    fun setSubscription(type: Int) {
        prefs.edit().putInt("subscription_type", type).apply()
    }

    // Сброс подписки
    fun clearSubscription() {
        prefs.edit().remove("subscription_type").apply()
    }
}