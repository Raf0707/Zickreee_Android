package raf.console.zickreee.domain.repository

import kotlinx.coroutines.flow.Flow
import raf.console.zickreee.domain.models.CounterItem

// domain/repository/CounterRepository.kt
interface CounterRepository {
    val allCounters: Flow<List<CounterItem>>
    suspend fun insertData(counterItem: CounterItem)
    suspend fun updateData(counterItem: CounterItem)
    suspend fun deleteData(counterItem: CounterItem)
    suspend fun findByName(title: String): CounterItem?
    suspend fun findByNames(title: String): List<CounterItem>
    fun findByTitle(title: String): Flow<List<CounterItem>>
    suspend fun getCounterById(id: Long): CounterItem?
}