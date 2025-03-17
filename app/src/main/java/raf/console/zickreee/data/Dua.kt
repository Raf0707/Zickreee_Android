package raf.console.zickreee.data

data class Dua(
    val arabic_dua: String,
    val transcript: String,
    val translate: String
)

data class EveryDayDua (
    val day: String,
    val arabic_dua: String,
    val transcript: String,
    val translate: String
)