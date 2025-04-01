package raf.console.zickreee.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import raf.console.zickreee.domain.models.CounterItem
import raf.console.zickreee.domain.use_cases.AddCounter
import raf.console.zickreee.domain.use_cases.DeleteCounter
import raf.console.zickreee.domain.use_cases.GetCounterById
import raf.console.zickreee.domain.use_cases.GetCounters
import raf.console.zickreee.domain.use_cases.UpdateCounter
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val getCounters: GetCounters,
    private val addCounter: AddCounter,
    private val updateCounter: UpdateCounter,
    private val deleteCounter: DeleteCounter,
    private val getCounterById: GetCounterById
) : ViewModel() {

    val counters = getCounters().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _currentCounter = MutableStateFlow<CounterItem?>(null)
    val currentCounter: StateFlow<CounterItem?> = _currentCounter.asStateFlow()

    fun setCurrentCounter(counter: CounterItem) {
        _currentCounter.value = counter
    }

    fun insert(title: String, target: Int) = viewModelScope.launch {
        addCounter(CounterItem(title = title, target = target, progress = 0))
    }

    fun insert(counter: CounterItem) = viewModelScope.launch {
        addCounter(counter)
    }

    fun update(counter: CounterItem) = viewModelScope.launch {
        updateCounter(counter)
    }

    fun delete(counter: CounterItem) = viewModelScope.launch {
        deleteCounter(counter)
    }

    suspend fun loadCounter(id: Long) {
        _currentCounter.value = getCounterById(id)
    }
}