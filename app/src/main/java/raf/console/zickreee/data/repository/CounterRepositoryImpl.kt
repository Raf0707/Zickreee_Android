package raf.console.zickreee.data.repository

import kotlinx.coroutines.flow.Flow
import raf.console.zickreee.data.local.dao.CounterDao
import raf.console.zickreee.domain.models.CounterItem
import raf.console.zickreee.domain.repository.CounterRepository

class CounterRepositoryImpl(private val counterDao: CounterDao) : CounterRepository {

    // Получение всех счетчиков как Flow
    override val allCounters: Flow<List<CounterItem>> = counterDao.getAllCounters()

    // Вставка нового счетчика
    override suspend fun insertData(counterItem: CounterItem) {
        counterDao.insertCounter(counterItem)
    }

    // Обновление счетчика
    override suspend fun updateData(counterItem: CounterItem) {
        counterDao.updateCounter(counterItem)
    }

    // Удаление счетчика
    override suspend fun deleteData(counterItem: CounterItem) {
        counterDao.deleteCounter(counterItem)
    }

    // Поиск по точному названию (синхронный)
    override suspend fun findByName(title: String): CounterItem? {
        return counterDao.findByName(title)
    }

    // Поиск по частичному совпадению названия (синхронный)
    override suspend fun findByNames(title: String): List<CounterItem> {
        return counterDao.findByNames(title)
    }

    // Поиск по частичному совпадению названия как Flow
    override fun findByTitle(title: String): Flow<List<CounterItem>> {
        return counterDao.findByTitle(title)
    }

    // Получение счетчика по ID
    override suspend fun getCounterById(id: Long): CounterItem? {
        return counterDao.getCounterById(id)
    }

    /*companion object {
        @Volatile
        private var INSTANCE: CounterRepositoryImpl? = null

        fun getInstance(application: Application): CounterRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                val database = CounterDatabase.getInstance(application)
                val instance = CounterRepositoryImpl(database.counterDao())
                INSTANCE = instance
                instance
            }
        }
    }*/
}