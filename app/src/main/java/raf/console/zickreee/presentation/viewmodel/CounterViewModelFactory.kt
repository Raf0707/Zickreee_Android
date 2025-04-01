package raf.console.zickreee.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import raf.console.zickreee.ZickreeeApp
import raf.console.zickreee.data.repository.CounterRepositoryImpl
import raf.console.zickreee.domain.use_cases.AddCounter
import raf.console.zickreee.domain.use_cases.DeleteCounter
import raf.console.zickreee.domain.use_cases.GetCounterById
import raf.console.zickreee.domain.use_cases.GetCounters
import raf.console.zickreee.domain.use_cases.UpdateCounter

class CounterViewModelFactory(
    private val getCounters: GetCounters,
    private val addCounter: AddCounter,
    private val updateCounter: UpdateCounter,
    private val deleteCounter: DeleteCounter,
    private val getCounterById: GetCounterById
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CounterViewModel(
            getCounters = getCounters,
            addCounter = addCounter,
            updateCounter = updateCounter,
            deleteCounter = deleteCounter,
            getCounterById = getCounterById
        ) as T
    }
}