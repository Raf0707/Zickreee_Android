package raf.console.zickreee.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import raf.console.zickreee.domain.models.Bookmark
import java.io.File

class BookmarkManager(private val context: Context) {
    private val bookmarksFile = "bookmarks.json"
    private val gson = Gson()
    private val bookmarksFlow = MutableStateFlow<List<Bookmark>>(emptyList())

    init {
        loadBookmarks()
    }

    private fun loadBookmarks() {
        try {
            val file = File(context.filesDir, bookmarksFile)
            if (file.exists()) {
                val jsonString = file.readText()
                val bookmarks = gson.fromJson<List<Bookmark>>(
                    jsonString,
                    object : TypeToken<List<Bookmark>>() {}.type
                ) ?: emptyList()
                bookmarksFlow.value = bookmarks
            }
        } catch (e: Exception) {
            Log.e("BookmarkManager", "Error loading bookmarks", e)
        }
    }

    fun getBookmarksFlow(): Flow<List<Bookmark>> = bookmarksFlow

    fun getAllBookmarks(): List<Bookmark> {
        return try {
            val jsonString = context.assets.open(bookmarksFile).bufferedReader().use { it.readText() }
            gson.fromJson(jsonString, object : TypeToken<List<Bookmark>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveBookmarks(bookmarks: List<Bookmark>) {
        try {
            val jsonString = gson.toJson(bookmarks)
            File(context.filesDir, bookmarksFile).writeText(jsonString)
            bookmarksFlow.value = bookmarks
        } catch (e: Exception) {
            Log.e("BookmarkManager", "Error saving bookmarks", e)
        }
    }

    fun addBookmark(bookmark: Bookmark) {
        val current = bookmarksFlow.value.toMutableList()
        if (!current.any { it.arabicDua == bookmark.arabicDua }) {
            current.add(bookmark)
            saveBookmarks(current)
        }
    }

    fun removeBookmark(arabicDua: String) {
        val current = bookmarksFlow.value.toMutableList()
        current.removeAll { it.arabicDua == arabicDua }
        saveBookmarks(current)
    }

    fun isBookmarked(arabicDua: String): Boolean {
        return bookmarksFlow.value.any { it.arabicDua == arabicDua }
    }
}