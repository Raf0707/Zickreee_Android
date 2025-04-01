package raf.console.zickreee.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import raf.console.zickreee.domain.models.CounterItem

@Dao
interface CounterDao {
    @Query("SELECT * FROM counters")
    fun getAllCounters(): Flow<List<CounterItem>> // Основное изменение - используем Flow

    @Query("SELECT * FROM counters WHERE id IN (:userIds)")
     fun loadAllByIds(userIds: IntArray): List<CounterItem>

    @Query("SELECT * FROM counters WHERE title LIKE :title LIMIT 1")
     fun findByName(title: String): CounterItem?

    @Query("SELECT * FROM counters WHERE title LIKE :title")
     fun findByNames(title: String): List<CounterItem>

    @Query("SELECT * FROM counters WHERE title LIKE :title")
    fun findByTitle(title: String): Flow<List<CounterItem>>

    @Query("SELECT * FROM counters WHERE id = :id")
     fun getCounterById(id: Long): CounterItem?

    @Insert
     fun insertAll(vararg counterItems: CounterItem)

    @Insert
     fun insertCounter(counterItem: CounterItem)

    @Update
     fun updateCounter(counterItem: CounterItem)

    @Delete
     fun deleteCounter(counterItem: CounterItem)
}