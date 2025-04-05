package raf.console.zickreee.domain.models

data class Bookmark(
    val day: String? = null,
    val arabicDua: String,
    val transcript: String,
    val translate: String,
    val additionalInfo: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)