package raf.console.zickreee.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import raf.console.zickreee.data.local.dao.CounterDao
import raf.console.zickreee.domain.models.CounterItem

@Database(entities = [CounterItem::class], version = 1)
abstract class CounterDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao

    /*companion object {
        @Volatile
        private var INSTANCE: CounterDatabase? = null

        fun getInstance(context: Context): CounterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CounterDatabase::class.java,
                    "counter_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }*/
}
