package raf.console.zickreee.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import raf.console.zickreee.data.Dua
import raf.console.zickreee.data.EveryDayDua

fun loadDuasFromAssets(context: Context, fileName: String): List<Dua> {
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val listType = object : TypeToken<List<Dua>>() {}.type
    return Gson().fromJson(jsonString, listType)
}

fun loadEveryDayDuasFromAssets(context: Context, fileName: String): List<EveryDayDua> {
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val listType = object : TypeToken<List<EveryDayDua>>() {}.type
    return Gson().fromJson(jsonString, listType)
}