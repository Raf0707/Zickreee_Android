package raf.console.zickreee.domain.use_cases

import kotlinx.coroutines.flow.Flow
import raf.console.zickreee.domain.models.CounterItem
import raf.console.zickreee.domain.repository.CounterRepository
import javax.inject.Inject

// domain/usecase/GetCounters.kt
class GetCounters @Inject constructor(
    private val repository: CounterRepository
) {
    operator fun invoke(): Flow<List<CounterItem>> = repository.allCounters
}

// domain/usecase/AddCounter.kt
class AddCounter @Inject constructor(
    private val repository: CounterRepository
) {
    suspend operator fun invoke(counter: CounterItem) {
        repository.insertData(counter)
    }
}

// domain/usecase/UpdateCounter.kt
class UpdateCounter @Inject constructor(
    private val repository: CounterRepository
) {
    suspend operator fun invoke(counter: CounterItem) {
        repository.updateData(counter)
    }
}

// domain/usecase/DeleteCounter.kt
class DeleteCounter @Inject constructor(
    private val repository: CounterRepository
) {
    suspend operator fun invoke(counter: CounterItem) {
        repository.deleteData(counter)
    }
}

// domain/usecase/GetCounterById.kt
class GetCounterById @Inject constructor(
    private val repository: CounterRepository
) {
    suspend operator fun invoke(id: Long): CounterItem? {
        return repository.getCounterById(id)
    }
}